package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.data.dto.folder.MoveFolderRequestDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folder.MoveFolderResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folderStructure.FolderStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FolderAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.model.folderStructure.UserStructure;
import br.com.joaojunio.cloudkeeper.service.FolderStructureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(value = "/api/folderStructure/v1")
public class FolderStructureController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FolderStructureService service;

    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<List<FolderStructureDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/{userId}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<?> getFolderStructure(@PathVariable("userId") Long userId) {
        try {
            String filePath = "C:/Temp/cloudkeeper/folder_structure/user_" + userId + ".json";
            File file = new File(filePath);

            UserStructure userStructure = objectMapper.readValue(file, UserStructure.class);

            return ResponseEntity.ok(userStructure);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("File not found for user id: " + userId);
        }
    }

    @GetMapping(
        value = "/{userId}/{newFolderName}/{folderId}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<FolderAddedToTheStructureDTO> createFolder(
        @PathVariable("userId") Long userId,
        @PathVariable("newFolderName") String newFolderName,
        @PathVariable("folderId") Long folderId,
        @RequestParam("folderName") String folderName
    ) {
        return ResponseEntity.ok().body(
            service.createFolderInStructure(
                new FolderAddedToTheStructureDTO(userId, newFolderName, folderName, folderId)
            )
        );
    }

    @GetMapping(
        value = "/{userId}/{folderName}/{folderId}/{newFolderName}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<MoveFolderResponseDTO> moveFolder(
        @PathVariable("userId") Long userId,
        @PathVariable("folderName") String folderName,
        @PathVariable("folderId") String folderId,
        @PathVariable("newFolderName") String newFolderName
    ) {
        return ResponseEntity.ok().body(
            service.moveFolder(
                new MoveFolderRequestDTO(userId, folderName, folderId, newFolderName)
            )
        );
    }
}
