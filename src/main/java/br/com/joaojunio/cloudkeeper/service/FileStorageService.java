package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FileStorageConfig;
import br.com.joaojunio.cloudkeeper.data.dto.file.FileCreateRequestDTO;
import br.com.joaojunio.cloudkeeper.exceptions.FileStorageException;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FolderNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageService {

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class.getName());

    @Autowired
    private FileService fileService;

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
         Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
         fileStorageLocation = path;

         try {
             logger.info("Creating Directories");
             Files.createDirectories(fileStorageLocation);
         }
         catch (Exception e) {
             throw new FileStorageException("Sorry! Error in creating Directories", e);
         }
    }

    public String storeFile(MultipartFile file, Long userId, String folderName) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            logger.info("Coping file of memory");

            String pathToSaveUserFile = createUniqueFolderForUserToSaveFiles(this.fileStorageLocation, userId);



            Path newPathToSaveTheFile = Paths.get(pathToSaveUserFile).toAbsolutePath().normalize();

            Path targetLocation = newPathToSaveTheFile.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileCreateRequestDTO fileCreateDTO = new FileCreateRequestDTO();
            fileCreateDTO.setName(fileName);
            fileCreateDTO.setSize(file.getSize());
            fileCreateDTO.setType(file.getContentType());
            fileCreateDTO.setStorageLocation(String.valueOf(targetLocation));
            fileCreateDTO.setUserId(userId);
            fileService.create(fileCreateDTO);

            return fileName;
        }
        catch (Exception e) {
            throw new FileStorageException("Sorry! Error in saving store file for memory", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            logger.info("Loading file as return Resource");

            Path filePath = this.fileStorageLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        }
        catch (Exception e) {
            throw new FileStorageException("Sorry! Error in load file for return resource", e);
        }
    }

    public FolderNode createNewFolder(Long userId, String folderNameExists, String name) {
        try {
            Optional<Path> found = findFolder(folderNameExists, userId);

            if (found.isPresent()) {
                Path folderPath = found.get();
                String pathNewFolder = folderPath + "/" + name;
                File newFolder = new File(pathNewFolder);

                if (!newFolder.exists()) {
                    newFolder.mkdirs();
                }
            }
            else {
                String pathNewFolder = fileStorageLocation + "/" + name;
                File newFolder = new File(pathNewFolder);

                if (!newFolder.exists()) {
                    newFolder.mkdirs();
                }
            }

            return new FolderNode(name);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Optional<Path> findFolder(String folderName, Long userId) throws IOException {
        Path pathUserFolder = Path.of(fileStorageLocation + "/" + userId);
        try (var paths = Files.walk(pathUserFolder)) {
            return paths
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().equals(folderName))
                .findFirst();
        }
    }

    private String createUniqueFolderForUserToSaveFiles(Path fileStorageLocation, Long userId) {
        try {
            String pathToSaveUserFile = fileStorageLocation + "/" + userId;
            File newFolderOfUser = new File(pathToSaveUserFile);

            if (!newFolderOfUser.exists()) {
                boolean success = newFolderOfUser.mkdirs();
                if (!success) {
                    throw new IllegalArgumentException("Impossible create new Folder of User");
                }
            }

            return pathToSaveUserFile;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}