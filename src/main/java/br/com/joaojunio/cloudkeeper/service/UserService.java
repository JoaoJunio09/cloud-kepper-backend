package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;
import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseListObjects;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public List<User> findAll() {
        return null;
    }

    public User findById(Long id) {

        logger.info("Finding one User");

        return mockDataUser();
    }

    public User create(User user) {

        logger.info("Creating new User");

        return repository.save(user);
    }

    private User mockDataUser() {
        return new User(
            1L,
            "João Junio",
            "Trindade Castro",
            "joaojunio1=818@gmail.com",
            "1234567",
            true
        );
    }
}
