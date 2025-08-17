package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FileStorageConfig;
import br.com.joaojunio.cloudkeeper.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class.getName());

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

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            logger.info("Coping file of memory");

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
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
}
