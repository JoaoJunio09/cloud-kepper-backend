package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.json.FileAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FolderAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.GeneratedJsonObjectResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.ObjectToGenerateJsonDTO;
import br.com.joaojunio.cloudkeeper.model.folderStructure.UserStructure;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FileNode;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FolderNode;
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
    private final String folderStructurePath = "";

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
            File file = Paths.get(object.getPath(), fileName).toFile();

            objectMapper.writeValue(file, userStructure);

            String folderStructurePath = this.folderStructurePath + "/" + fileName;

            return new GeneratedJsonObjectResponseDTO(
                "Json object successfully generated",
                folderStructurePath,
                new Date()
            );
        }
        catch (Exception e) {
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

    public void addFile(FileAddedToTheStructureDTO fileAdded) {

        logger.info("Creating a new File in folder structure");

        try {
            File file = new File(folderStructurePath + "/user_" + fileAdded.getUserId() + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.structure.get("root"),
                FolderNode.class
            );

            FileNode newFile = new FileNode(
                fileAdded.getName(),
                fileAdded.getLocalStorage(),
                fileAdded.getLocalStorage(),
                fileAdded.getSize()
            );
            rootFolder.addChild(newFile);

            userStructure.structure.put("root", rootFolder);

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
