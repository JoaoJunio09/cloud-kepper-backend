package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.data.dto.file.MoveFileResponseDTO;
import br.com.joaojunio.cloudkeeper.data.dto.folderStructure.FolderStructureDTO;
import br.com.joaojunio.cloudkeeper.data.dto.json.FolderAddedToTheStructureDTO;
import br.com.joaojunio.cloudkeeper.service.FolderStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/folderStructure/v1")
public class FolderStructureController {

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
        value = "/{userId}/{newFolderName}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<FolderAddedToTheStructureDTO> createFolder(
        @PathVariable("userId") Long userId,
        @PathVariable("newFolderName") String newFolderName,
        @RequestParam("folderName") String folderName
    ) {
        return ResponseEntity.ok().body(
            service.createFolderInStructure(
                new FolderAddedToTheStructureDTO(userId, newFolderName, folderName)
            )
        );
    }

    @GetMapping(
        value = "/{userId}/{fileId}/{nameFolder}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<MoveFileResponseDTO> moveFileToOtherFolder(
        @PathVariable("userId") Long userId,
        @PathVariable("fileId") String fileId,
        @PathVariable("nameFolder") String nameFolder
    ) {
        return ResponseEntity.ok().body(service.moveFile(userId, fileId, nameFolder));
    }
}
