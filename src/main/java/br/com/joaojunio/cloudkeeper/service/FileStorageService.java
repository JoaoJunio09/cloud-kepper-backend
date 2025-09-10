package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.file.DeleteFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.file.MoveFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FileAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FileRemovedFromStructure;
import br.com.joaojunio.cloudkeeper.exceptions.FileStorageException;
import com.backblaze.b2.client.structures.B2FileVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class FileStorageService {

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class.getName());

    @Autowired
    private FileService fileService;

    @Autowired
    private CloudFileStorageService cloudFileService;

    @Autowired
    private LocalFileStorageService localFileService;

    @Autowired
    private JsonStorageService jsonStorageService;

    public UploadFileResponseDTO upload(MultipartFile file, Long userId, String folderName) {
        logger.info("Uploading Files");

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            B2FileVersion fileVersion = cloudFileService.uploadFile(file);
            localFileService.storeFile(file, userId);

            jsonStorageService.addFile(
                new FileAddedToTheStructureDTO(
                    fileVersion.getFileId(),
                    userId,
                    file.getContentType(),
                    fileName,
                    file.getSize()
                ), folderName
            );

            UploadFileResponseDTO responseDTO = new UploadFileResponseDTO();
            responseDTO.setFileName(fileVersion.getFileName());
            responseDTO.setSize(fileVersion.getContentLength());
            responseDTO.setType(fileVersion.getContentType());
            responseDTO.setTimestamp(fileVersion.getUploadTimestamp());

            return responseDTO;
        }
        catch (Exception e) {
            throw new FileStorageException("Sorry! Unable to upload file: " + fileName);
        }
    }

    public Resource download(String fileId) {
        logger.info("Downloading Files");

        try {
            Resource resource = cloudFileService.downloadFile(fileId);

            if (!resource.exists()) {
                throw new IllegalArgumentException("Resource for file is null");
            }

            return resource;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Sorry! Error in downloading file");
        }
    }

    public DeleteFileResponseDTO delete(Long userId, String fileId) {
        logger.info("Deleting one File");

        try {
            if (fileId.trim().equals("")) {
                logger.info("File Id is empty or null!");
            }

            boolean fileRemovingForFolderStructure = jsonStorageService.removeFile(
                new FileRemovedFromStructure(userId, fileId)
            );

            B2FileVersion fileVersion = fileRemovingForFolderStructure
                ? cloudFileService.deleteFile(fileId)
                : null;

            if (fileVersion == null) {
                logger.error("Error in deleting file");
                throw new RuntimeException("Error in deleting file");
            }

            return new DeleteFileResponseDTO(
                "File deleted successfully!",
                fileVersion.getFileName(),
                fileVersion.getContentType(),
                fileVersion.getContentLength()
            );
        }
        catch (Exception e) {
            throw new FileStorageException("Sorry! Error in deleting one file");
        }
    }

    public MoveFileResponseDTO moveFile(Long userId, String fileId, String folderName) {
        try {
            if (fileId.equalsIgnoreCase("") ||
                folderName.equalsIgnoreCase("") ||
                userId.equals("") || userId == null
            ) {
                throw new IllegalArgumentException("Parameters cannot be empty or null");
            }

            jsonStorageService.moveFile(userId, fileId, folderName);
            return new MoveFileResponseDTO();
        }
        catch (Exception e) {
            throw new FileStorageException("Sorry! Error in moving file to another folder");
        }
    }

    public String getFileName(String fileId) {
        try {
            B2FileVersion fileVersion = cloudFileService.getFileVersion(fileId);
            if (fileVersion != null && fileVersion.getFileName() != null) {
                return fileVersion.getFileName();
            }
        } catch (Exception e) {
            logger.error("Could not get fileName for fileId {}", fileId, e);
        }
        return "unknown-file";
    }

}