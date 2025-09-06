package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FileStorageConfig;
import br.com.joaojunio.cloudkeeper.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalFileStorageService {

    private final Logger logger = LoggerFactory.getLogger(LocalFileStorageService.class.getName());

    private final Path fileStorageLocation;

    @Autowired
    public LocalFileStorageService(FileStorageConfig fileStorageConfig) {
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

    public String storeFile(MultipartFile file, Long userId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            logger.info("Coping file of memory");

            String pathToSaveUserFile = createUniqueFolderForUserToSaveFiles(this.fileStorageLocation, userId);
            Path newPathToSaveTheFile = Paths.get(pathToSaveUserFile).toAbsolutePath().normalize();

            Path targetLocation = newPathToSaveTheFile.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }
        catch (Exception e) {
            throw new FileStorageException("Sorry! Error in saving store file for memory", e);
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

    public Resource downloadFile(String fileName) {
        return null;
    }
}
