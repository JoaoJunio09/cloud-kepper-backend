package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user/v1")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public User create(@RequestBody User user) {
        return service.create(user);
    }

}
