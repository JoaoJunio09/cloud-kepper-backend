package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.config.FileStorageConfig;
import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FileAddedToTheStructureDTO;
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
            jsonStorageService.addFile(
                new FileAddedToTheStructureDTO(
                    userId,
                    file.getContentType(),
                    fileName,
                    file.getSize()
                ), folderName
            );

            B2FileVersion fileVersion = cloudFileService.uploadFile(file);

            localFileService.storeFile(file, userId);

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

    public Resource download(String fileName) {

    }
}