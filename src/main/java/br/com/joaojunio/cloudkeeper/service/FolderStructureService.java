package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FolderStructurePathConfig;
import br.com.joaojunio.cloudkeeper.data.dto.folderStructure.FolderStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.ObjectToGenerateJsonDTO;
import br.com.joaojunio.cloudkeeper.data.dto.user.UserDTO;
import br.com.joaojunio.cloudkeeper.model.FolderStructure;
import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.FolderStructureRepository;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;
import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseListObjects;

@Service
public class FolderStructureService {

    private final Logger logger = LoggerFactory.getLogger(FolderStructureService.class);

    @Autowired
    private JsonStorageService jsonStorageService;

    @Autowired
    private FolderStructurePathConfig path;

    @Autowired
    FolderStructureRepository repository;

    @Transactional(readOnly = true)
    public List<FolderStructureDTO> findAll() {

        logger.info("Finding All Folders Structure");

        return parseListObjects(repository.findAll(), FolderStructureDTO.class);
    }

    public void createUserFolderStructure(UserDTO user) {
        try {
            logger.info("Saving the user's Folder Structure in the Database");

            var objectGenerated = jsonStorageService.generateJsonOfFolderStructure(
                new ObjectToGenerateJsonDTO(user.getId(), user.getFirstName(), path)
            );

            FolderStructure folderStructure = new FolderStructure();
            folderStructure.setFolderStructurePath(objectGenerated.getSavedPath());
            folderStructure.setUser(parseObject(user, User.class));

            repository.save(folderStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void addFolder(Long userId, String name) {

        logger.info("Creating a new Folder");

        try {
            File file = new File(path + "user_" + userId + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = (FolderNode) userStructure.structure.get("root");

            FolderNode newFolder = new FolderNode(name);
            rootFolder.addChild(newFolder);

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void addFile(Long userId, String type, String name, String localStorage, Long size) {

        logger.info("Creating a new File");

        try {
            File file = new File(path + "/user_" + userId + ".json");

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            FolderNode rootFolder = objectMapper.convertValue(
                userStructure.structure.get("root"),
                FolderNode.class
            );

            FileNode newFile = new FileNode(name, localStorage, type, size);
            rootFolder.addChild(newFile);

            userStructure.structure.put("root", rootFolder);

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(file, userStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
