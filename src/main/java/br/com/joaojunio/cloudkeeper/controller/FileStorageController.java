package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.controller.docs.FileStorageControllerDocs;
import br.com.joaojunio.cloudkeeper.data.dto.file.UploadFileResponseDTO;
import br.com.joaojunio.cloudkeeper.model.folderStructure.node.FolderNode;
import br.com.joaojunio.cloudkeeper.service.FileStorageService;
import br.com.joaojunio.cloudkeeper.service.FolderStructureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/file/v1")
@Tag(name = "File Endpoint")
public class FileStorageController implements FileStorageControllerDocs {

    private final Logger logger = LoggerFactory.getLogger(FileStorageController.class.getName());

    @Autowired
    private FileStorageService service;

    @Autowired
    private FolderStructureService folderStructureService;

    @PostMapping(value = "/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestParam("folderName") String folderName
    ) {
        var fileName = service.storeFile(file, 8L, folderName);

        //folderStructureService.addFile(
          //  8L,
            //file.getContentType(),
            //fileName,
            //"C:/Temp/cloudkeeper/files/8/" + fileName,
            //file.getSize()
        //);

        var downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/file/v1/downloadFile/")
            .path(fileName)
            .toUriString();
        return new UploadFileResponseDTO(fileName, downloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping(value = "/uploadMultipleFile")
    @Override
    public List<UploadFileResponseDTO> uploadMulitpleFiles(
        @RequestParam("files") MultipartFile[] files
    ) {
        //return Arrays.stream(files)
          //  .map(file -> uploadFile(file))
            //.collect(Collectors.toList());
        return null;
    }

    @GetMapping(value = "/downloadFile/..+")
    @Override
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (Exception e) {
            logger.error("Could not determine file type!");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\""
            )
            .body(resource);
    }

    @GetMapping(
        value = "/addNewFolder",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<FolderNode> createFolder(
        @RequestParam("id") Long userId,
        @RequestParam("folderNameExists") String folderNameExists,
        @RequestParam("folderName") String folderName
    ) {
        return ResponseEntity.ok().body(service.createNewFolder(userId ,folderNameExists, folderName));
    }


}
