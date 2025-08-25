package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FolderStructurePathConfig;
import br.com.joaojunio.cloudkeeper.data.dto.folderStructure.FolderStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.ObjectToGenerateJsonDTO;
import br.com.joaojunio.cloudkeeper.data.dto.user.UserDTO;
import br.com.joaojunio.cloudkeeper.model.FolderStructure;
import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.FolderStructureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;
import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseListObjects;

@Service
public class FolderStructureService {

    private final Logger logger = LoggerFactory.getLogger(FolderStructureService.class.getName());

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
}
