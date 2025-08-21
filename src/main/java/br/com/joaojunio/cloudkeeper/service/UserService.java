package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.user.UserDTO;
import br.com.joaojunio.cloudkeeper.exceptions.NotFoundException;
import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;
import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseListObjects;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public List<UserDTO> findAll() {

        logger.info("Finding all User");

        return parseListObjects(repository.findAll(), UserDTO.class);
    }

    public UserDTO findById(Long id) {

        logger.info("Finding one User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));

        var dto = parseObject(entity, UserDTO.class);
        return dto;
    }

    public UserDTO create(UserDTO user) {

        logger.info("Creating new User");

        var entity = parseObject(user, User.class);
        var entitySaved = repository.save(entity);

        if (entitySaved == null) {
            throw new IllegalArgumentException("User Entity of null");
        }



        return parseObject(entitySaved, UserDTO.class);
    }



    public UserDTO update(UserDTO user) {

        logger.info("Updating a User");

        var entity = repository.findById(user.getId())
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + user.getId()));
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setEnabled(user.getEnabled());

        var dto = parseObject(repository.save(entity), UserDTO.class);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));
        repository.delete(entity);
    }
}
