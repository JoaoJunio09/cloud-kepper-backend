package br.com.joaojunio.cloudkeeper.controller;

import br.com.joaojunio.cloudkeeper.data.dto.security.AccountCredentialsDTO;
import br.com.joaojunio.cloudkeeper.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Endpoint!")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Operation
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDTO credentials) {
        if (credentialsIsValid(credentials)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = service.signIn(credentials);

        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        return ResponseEntity.ok().body(token);
    }

    private boolean credentialsIsValid(AccountCredentialsDTO credentials) {
        return credentials == null ||
            StringUtils.isBlank(credentials.getPassword()) ||
            StringUtils.isBlank(credentials.getUsername());
    }
}
