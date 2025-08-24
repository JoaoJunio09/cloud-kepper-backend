package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.data.dto.folderStructure.FolderStructureDTO;
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

    @PostMapping(
        value = "/addFolder",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<?> createNewFolderOrAddNewFile(
        @PathVariable("userId") Long userId,
        @RequestParam("name") String name
        // HttpSession session
    ) {
        // Long userId = (Long) session.getAttribute("userId");
        service.addFolder(2L, name);
        return ResponseEntity.ok().body("Folder creating success!");
    }
}
