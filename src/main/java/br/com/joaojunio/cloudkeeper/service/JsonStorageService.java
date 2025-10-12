package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.file.MoveFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folder.MoveFolderRequestDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folder.MoveFolderResponseDTO;
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

            boolean added = addFolderToStructure(rootFolder, folderAdded.getFolderName(), newFolder, folderAdded.getFolderId());
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

    private boolean addFolderToStructure(FolderNode currentNode, String folderName, FolderNode newFolder, Long folderId) {
        if (currentNode.getName().trim().equalsIgnoreCase(folderName) && currentNode.getId().equals(String.valueOf(folderId))) {
            currentNode.getChildren().add(newFolder);
            return true;
        }

        for (Object child : currentNode.getChildren()) {
            if (child instanceof FolderNode folderChild) {
                boolean added = addFolderToStructure(folderChild, folderName, newFolder, folderId);
                if (added) return true;
            }
        }

        return false;
    }

    public void addFile(FileAddedToTheStructureDTO fileAdded, String folderName, Long folderId) {
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

            boolean added = addFileToFolder(rootFolder, folderName, newFile, folderId);
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

    private boolean addFileToFolder(FolderNode currentFolder, String folderName, FileNode newFile, Long folderId) {
        if (currentFolder.getName().trim().equalsIgnoreCase(folderName) && currentFolder.getId().equals(String.valueOf(folderId))) {
            currentFolder.addChild(newFile);
            return true;
        }

        for (Object child : currentFolder.getChildren()) {
            if (child instanceof FolderNode folderChild) {
                boolean added = addFileToFolder(folderChild, folderName, newFile, folderId);
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

    public MoveFileResponseDTO moveFile(Long userId, String fileId, String nameFolder, Long folderId) throws IOException {
        logger.info("Manipulating json to change file from one folder to another");

        try {
            File file = new File(folderStructurePath + "/user_" + userId + ".json");

            UserStructure structure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                structure.getStructure().get("root"),
                FolderNode.class
            );

            FileNode fileNode = getFileNode(rootFolder, fileId);

            if (fileNode.getFileType() == null || fileNode.getSize() == null || fileNode.getName() == null) {
                logger.error("File type, size and name for file is null.");
                fileNode = getFileNode(rootFolder, fileId);
            }

            if (fileNode.getFileType() == null || fileNode.getSize() == null || fileNode.getName() == null) {
                throw new Exception("Critical error: unable to move file to another folder");
            }

            Map<String, Object> objectRemoved = removeFile(new FileRemovedFromStructure(userId, fileId));

            if (objectRemoved.get("removed").equals(true)) {
                addFile(new FileAddedToTheStructureDTO(
                    fileId,
                    userId,
                    fileNode.getFileType(),
                    fileNode.getName(),
                    fileNode.getSize()
                ), nameFolder, folderId);
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
        for (Node child : currentNode.getChildren()) {
            if (child instanceof FileNode fileNode) {
                if (fileNode.getFileId().equalsIgnoreCase(fileId)) {
                    return fileNode;
                }
            } else if (child instanceof FolderNode folderNode) {
                FileNode found = getFileNode(folderNode, fileId);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public MoveFolderResponseDTO moveFolder(MoveFolderRequestDTO moveFolder) {
        try {
            File file = new File(folderStructurePath + "/user_" + moveFolder.getUserId() + ".json");

            UserStructure structure = objectMapper.readValue(file, UserStructure.class);

            FolderNode root = objectMapper.convertValue(
                structure.getStructure().get("root"),
                FolderNode.class
            );

            FolderNode folderNode = getFolder(root, moveFolder.getFolderId());

            boolean removedFolder = removeFolder(moveFolder.getUserId(), root, moveFolder.getFolderId(), folderNode);

            if (!removedFolder) {
                throw new Exception("Critical error: unable to remove folder to another folder");
            }

            var addedFolder = addFolder(moveFolder.getUserId(), root, moveFolder.getCurrentFolderId(), folderNode);

            if (addedFolder == null) {
                throw new Exception("Critical error: unable to add folder to another folder");
            }

            return new MoveFolderResponseDTO(folderNode.getName(), folderNode.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public FolderNode addFolder(Long userId, FolderNode currentFolder, String folderId, FolderNode folderNode) {
        try {
            File file = new File(folderStructurePath + "/user_" + userId + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.getStructure().get("root"),
                FolderNode.class
            );

            FolderNode addedFolder = addToFolder(rootFolder, folderId, folderNode);

            userStructure.getStructure().put("root", objectMapper.convertValue(rootFolder, JsonNode.class));
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);

            logger.info("Successfully added folder in JSON!");
            return addedFolder;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FolderNode addToFolder(FolderNode currentFolder, String folderId, FolderNode folderNode) {
        if (currentFolder.getId().equals(String.valueOf(folderId))) {
            currentFolder.addChild(folderNode);
            return currentFolder;
        }

        for (Node child : currentFolder.getChildren()) {
            if (child instanceof FolderNode folderChild) {
                FolderNode found = addToFolder(folderChild, folderId, folderNode);
                if (found != null) return found;
            }
        }
        return null;
    }

    public boolean removeFolder(Long userId, FolderNode currentFolder, String folderId, FolderNode folderRemoved) {
        try {
            File file = new File(folderStructurePath + "/user_" + userId + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.getStructure().get("root"),
                FolderNode.class
            );

            boolean removed = removeToFolder(rootFolder, folderId, folderRemoved);

            userStructure.getStructure().put("root", objectMapper.convertValue(rootFolder, JsonNode.class));
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);

            logger.info("Successfully remove folder in JSON!");
            return removed;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeToFolder(FolderNode currentFolder, String folderId, FolderNode folderRemoved) {
        Iterator<Node> iterator = currentFolder.getChildren().iterator();

        while (iterator.hasNext()) {
            Node child = iterator.next();

            if (child instanceof FolderNode folderNode) {
                if (folderNode.getId().equalsIgnoreCase(folderId)) {
                    iterator.remove();
                    return true;
                }

                boolean removed = removeToFolder(folderNode, folderId, folderRemoved);
                if (removed) return true;
            }
        }

        return false;
    }


    public FolderNode getFolder(FolderNode currentFolder, String folderId) {
        if (currentFolder.getId().equalsIgnoreCase(folderId)) {
            return currentFolder;
        }

        for (Node child : currentFolder.getChildren()) {
            if (child instanceof FolderNode folderNode) {
                FolderNode found = getFolder(folderNode, folderId);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

}
