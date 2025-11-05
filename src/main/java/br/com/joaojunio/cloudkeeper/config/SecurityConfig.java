package br.com.joaojunio.cloudkeeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;


public class SecurityConfig {

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(
//                "", 8, ""
//        );
//
//        return passwordEncoder;
//    }
}
