package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.model.User;
import br.com.joaojunio.cloudkeeper.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;
import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseListObjects;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User create(User user) {

        return null;
    }
}
