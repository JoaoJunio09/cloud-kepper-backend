package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.controller.docs.FileStorageControllerDocs;
import br.com.joaojunio.cloudkeeper.data.dto.file.DeleteFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import br.com.joaojunio.cloudkeeper.service.CloudFileStorageService;
import br.com.joaojunio.cloudkeeper.service.FileStorageService;
import br.com.joaojunio.cloudkeeper.service.FolderStructureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
@RestController
@RequestMapping(value = "api/file/v1")
@Tag(name = "File Endpoint")
public class FileStorageController implements FileStorageControllerDocs {

    private final Logger logger = LoggerFactory.getLogger(FileStorageController.class.getName());

    @Autowired
    private FileStorageService service;

    @Autowired
    private CloudFileStorageService cloudFileStorageService;

    @Autowired
    private FolderStructureService folderStructureService;

    @PostMapping(value = "/uploadFile/{id}")
    @Override
    public ResponseEntity<UploadFileResponseDTO> uploadFile(
        @PathVariable("id") Long id,
        @RequestParam("file") MultipartFile file,
        @RequestParam("folderName") String folderName
    ) {
        return ResponseEntity.ok().body(service.upload(file, id, folderName));
    }

    @GetMapping(value = "/{type}/{fileId}")
    @Override
    public ResponseEntity<Resource> downloadFile(
        @PathVariable("type") String type,
        @PathVariable("fileId") String fileId
    ) {
        Resource resource = service.download(fileId);

        String fileName = "";
        try {
            fileName = service.getFileName(fileId);
        }
        catch (Exception e) {
            logger.error("Error in fileName");
        }

        String contentType = URLConnection.guessContentTypeFromName(fileName);
        contentType = contentType == null ? "application/octet-stream" : "";

        String dispositon = type.equals("preview")
            ? "inline"
            : "attachment";

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                dispositon + "; filename=\"" + fileName + "\""
            )
            .body(resource);
    }

    @DeleteMapping(value = "/{fileId}")
    @Override
    public ResponseEntity<DeleteFileResponseDTO> delete(@PathVariable("fileId") String fileId) {
        return ResponseEntity.ok().body(service.delete(fileId));
    }
}
