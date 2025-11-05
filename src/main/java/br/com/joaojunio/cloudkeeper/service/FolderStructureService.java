package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FolderStructurePathConfig;
import br.com.joaojunio.cloudkeeper.data.dto.folder.MoveFolderRequestDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folder.MoveFolderResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folderStructure.FolderStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FolderAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.ObjectToGenerateJsonDTO;
import br.com.joaojunio.cloudkeeper.data.dto.person.PersonDTO;
import br.com.joaojunio.cloudkeeper.model.FolderStructure;
import br.com.joaojunio.cloudkeeper.model.Person;
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

    public void createPersonFolderStructure(PersonDTO person) {
        try {
            logger.info("Saving the person's Folder Structure in the Database");

            var objectGenerated = jsonStorageService.generateJsonOfFolderStructure(
                new ObjectToGenerateJsonDTO(person.getId(), person.getFirstName(), path)
            );

            FolderStructure folderStructure = new FolderStructure();
            folderStructure.setFolderStructurePath(objectGenerated.getSavedPath());
            folderStructure.setPerson(parseObject(person, Person.class));

            repository.save(folderStructure);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public FolderAddedToTheStructureDTO createFolderInStructure(FolderAddedToTheStructureDTO folderAdded) {
        logger.info("Creating new Folder in structure");

        try {
            if (folderAdded.getNewFolderName().equalsIgnoreCase("")) {
                throw new IllegalArgumentException("Error: New folder name is empty!");
            }

            jsonStorageService.addFolder(folderAdded);
            return folderAdded;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public MoveFolderResponseDTO moveFolder(MoveFolderRequestDTO moveFolder) {
        logger.info("Moving a folder a other folder");

        try {
            if (moveFolder == null) {
                throw new IllegalArgumentException("Error: object for folder is empty!");
            }

            return jsonStorageService.moveFolder(moveFolder);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
