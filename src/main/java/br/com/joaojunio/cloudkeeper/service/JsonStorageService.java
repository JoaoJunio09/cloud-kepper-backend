package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.json.FileAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FolderAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.GeneratedJsonObjectResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.ObjectToGenerateJsonDTO;
import br.com.joaojunio.cloudkeeper.model.folderStructure.UserStructure;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FileNode;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FolderNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
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

        logger.info("Creating a new Folder in folder structure");

        try {
            File file = new File(folderStructurePath + "user_" + folderAdded.getUserId() + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = (FolderNode) userStructure.structure.get("root");

            FolderNode newFolder = new FolderNode(folderAdded.getName());
            rootFolder.addChild(newFolder);

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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
                fileAdded.getFileId(),
                fileAdded.getName(),
                fileAdded.getType(),
                fileAdded.getSize()
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
            JsonNode childNode = objectMapper.valueToTree(child);

            if ("folder".equals(childNode.get("type").asText())) {
                FolderNode folderChild = objectMapper.convertValue(childNode, FolderNode.class);

                boolean added = addFileToFolder(folderChild, folderName, newFile);
                if (added) {
                    currentFolder.getChildren().remove(child);
                    currentFolder.getChildren().add(folderChild);
                    return true;
                };
            }
        }

        return false;
    }

    public boolean removeFile(String fileName, String fileId) {
        return false;
    }
}
