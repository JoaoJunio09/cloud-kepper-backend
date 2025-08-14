package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user/v1")
public class UserController {

    @GetMapping
    public User getHello() {
        return new User(1L, "João Junio","Trindade Castro","joaojunio818@gmail.com", "13456", true);
    }
}
