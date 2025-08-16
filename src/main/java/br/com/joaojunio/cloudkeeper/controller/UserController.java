package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.data.dto.UserDTO;
import br.com.joaojunio.cloudkeeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/user/v1")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(
        consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
        return ResponseEntity.ok().body(service.create(user));
    }

    @PutMapping(
        consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO user) {
        return ResponseEntity.ok().body(service.update(user));
    }

    @DeleteMapping(
        value = "/{id}"
    )
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
