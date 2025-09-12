package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.file.MoveFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.*;
import br.com.joaojunio.cloudkeeper.model.folderStructure.UserStructure;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FileNode;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FolderNode;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.Node;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JsonStorageService {

    @Value("${file.folderStructure:default}")
    private String folderStructurePath = "";

    private final Logger logger = LoggerFactory.getLogger(JsonStorageService.class.getName());

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeneratedJsonObjectResponseDTO generateJsonOfFolderStructure(ObjectToGenerateJsonDTO object) {
        logger.info("Generating json object from folder strcture");

        if (object == null) {
            throw new IllegalArgumentException("Sorry! The Json Object is null, unable to save it.");
        }

        try {
            FolderNode rootFolder = new FolderNode("root");

            Map<String, Object> structure = new LinkedHashMap<>();
            structure.put("root", rootFolder);

            UserStructure userStructure = new UserStructure(object.getUserName(), object.getUserId());
            userStructure.structure = structure;

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            String fileName = "user_" + object.getUserId() + ".json";
            File file = Paths.get(object.getPath().getFolderStructure(), fileName).toFile();

            objectMapper.writeValue(file, userStructure);

            String folderStructurePath = this.folderStructurePath + "/" + fileName;

            return new GeneratedJsonObjectResponseDTO(
                "Json object successfully generated",
                folderStructurePath,
                new Date()
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InternalError("Sorry! Critical error generating json object");
        }
    }

    public void addFolder(FolderAddedToTheStructureDTO folderAdded) {
        logger.info("Manipulating json of structure to add new folder");

        try {
            File file = new File(folderStructurePath + "/user_" + folderAdded.getUserId() + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.getStructure().get("root"),
                FolderNode.class
            );

            FolderNode newFolder = new FolderNode(folderAdded.getNewFolderName());

            boolean added = addFolderToStructure(rootFolder, folderAdded.getFolderName(), newFolder);
            if (!added) {
                throw new RuntimeException("Folder '" + folderAdded.getFolderName() + "' not found!");
            }

            userStructure.getStructure().put("root", objectMapper.convertValue(rootFolder, JsonNode.class));

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private boolean addFolderToStructure(FolderNode currentNode, String folderName, FolderNode newFolder) {
        if (currentNode.getName().trim().equalsIgnoreCase(folderName)) {
            currentNode.getChildren().add(newFolder);
            return true;
        }

        for (Object child : currentNode.getChildren()) {
            if (child instanceof FolderNode folderChild && folderChild.getName().equalsIgnoreCase(folderName)) {
                boolean added = addFolderToStructure(folderChild, folderName, newFolder);
                if (added) return true;
            }
        }

        return false;
    }

    // vou adicionar o arquivo dentro da pasta (percorro o json e procuro ela)
    // se a pasta nao existir, o arquivo sera adicionado na pasta raiz
    public void addFile(FileAddedToTheStructureDTO fileAdded, String folderName) {
        logger.info("Creating a new File in folder structure");

        try {
            File file = new File(folderStructurePath + "/user_" + fileAdded.getUserId() + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.structure.get("root"),
                FolderNode.class
            );

            FileNode newFile = new FileNode(
                fileAdded.getFileId(), fileAdded.getName(), fileAdded.getType(), fileAdded.getSize()
            );

            boolean added = addFileToFolder(rootFolder, folderName, newFile);
            if (!added) {
                throw new RuntimeException("Folder '" + folderName + "' not found!");
            }

            userStructure.structure.put("root", objectMapper.convertValue(rootFolder, JsonNode.class));

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private boolean addFileToFolder(FolderNode currentFolder, String folderName, FileNode newFile) {
        if (currentFolder.getName().trim().equalsIgnoreCase(folderName)) {
            currentFolder.addChild(newFile);
            return true;
        }

        for (Object child : currentFolder.getChildren()) {
            if (child instanceof FolderNode folderChild) {
                boolean added = addFileToFolder(folderChild, folderName, newFile);
                if (added) {
                    return true;
                }
            }
        }

        return false;
    }

    public Map<String, Object> removeFile(FileRemovedFromStructure fileRemoved) {
        logger.info("Deleting one File in folder structure");

        try {
            File file = new File(folderStructurePath + "/user_" + fileRemoved.getUserId() + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.getStructure().get("root"),
                FolderNode.class
            );

            Map<String, Object> objectRemoved = removeToFile(rootFolder, fileRemoved.getFileId());
            if (!objectRemoved.get("removed").equals(true)) {
                logger.error("File to be deleted not found");
                throw new RuntimeException("File to be deleted not found");
            }

            userStructure.getStructure().put("root", objectMapper.convertValue(rootFolder, JsonNode.class));
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);

            logger.info("Successfully remove file in JSON!");
            return objectRemoved;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Map<String, Object> removeToFile(FolderNode currentFolder, String fileId) {
        Iterator<Node> iterator = currentFolder.getChildren().iterator();

        while (iterator.hasNext()) {
            Node child = iterator.next();

            if (child instanceof FileNode fileNode) {
                if (fileNode.getFileId().equals(fileId)) {
                    iterator.remove();
                    return Map.of(
                        "oldFolder", "root",
                        "removed", true
                    );
                }
            }
            else if (child instanceof FolderNode folderNode) {
                Map<String, Object> objectRemoved = removeToFile(folderNode, fileId);
                if (objectRemoved.get("removed").equals(true)) {
                    return Map.of(
                        "oldFolder", child.getName(),
                        "removed", true
                    );
                }
            }
        }
        return Map.of(
            "oldFolder", "",
            "removed", false
        );
    }

    public MoveFileResponseDTO moveFile(Long userId, String fileId, String nameFolder) throws IOException {
        logger.info("Manipulating json to change file from one folder to another");

        try {
            File file = new File(folderStructurePath + "/user_" + userId + ".json");

            UserStructure structure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                structure.getStructure().get("root"),
                FolderNode.class
            );

            FileNode fileNode = getFileNode(rootFolder, fileId);

            Map<String, Object> objectRemoved = removeFile(new FileRemovedFromStructure(userId, fileId));

            if (objectRemoved.get("removed").equals(true)) {
                addFile(new FileAddedToTheStructureDTO(
                    fileId,
                    userId,
                    fileNode.getFileType(),
                    fileNode.getName(),
                    fileNode.getSize()
                ), nameFolder);
            }

            return new MoveFileResponseDTO(
                fileNode.getName(),
                fileNode.getFileId(),
                (String) objectRemoved.get("oldFolder"),
                nameFolder
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public FileNode getFileNode(FolderNode currentNode, String fileId) {
        Iterator<Node> iterator = currentNode.getChildren().iterator();
        FileNode file = new FileNode();

        while (iterator.hasNext()) {
            Node child = iterator.next();
            if (child instanceof FolderNode childNode) {
                file = getFileNode(childNode, fileId);
            }
            if (child instanceof FileNode childNode && childNode.getFileId().equalsIgnoreCase(fileId)) {
                file = childNode;
            }
        }
        return file;
    }
}
