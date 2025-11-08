package br.com.joaojunio.cloudkeeper.config;

import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    User init() {
//        User user1 = new User("joaojunio123", "João Junio", "{pbkdf2}d75044d944f29f08d1b0656d25d25405bd5acf538e812087d2569f5353e9c47f3b2e78a10f72193a", true, true, true, true);
//        userRepository.save(user1);
        return null;
    }
}
