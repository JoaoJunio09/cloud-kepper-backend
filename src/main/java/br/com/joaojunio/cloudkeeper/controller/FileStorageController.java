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

import java.util.List;

@RestController
@RequestMapping(value = "api/file/v1")
@Tag(name = "File Endpoint")
public class FileStorageController implements FileStorageControllerDocs {

    private final Logger logger = LoggerFactory.getLogger(FileStorageController.class.getName());

    @Autowired
    private FileStorageService service;

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


}
