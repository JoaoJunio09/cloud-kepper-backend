package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.file.FileCreateRequestDTO;
import br.com.joaojunio.cloudkeeper.model.File;
import br.com.joaojunio.cloudkeeper.repositories.FileRepository;
import br.com.joaojunio.cloudkeeper.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;

@Service
public class FileService {

    private Logger logger = LoggerFactory.getLogger(FileService.class.getName());

    @Autowired
    FileRepository repository;

    @Autowired
    UserRepository userRepository;

    public void create(FileCreateRequestDTO file) {
        logger.info("Creating a new file");

        var user = userRepository.findById(file.getUserId())
            .orElseThrow(() -> new RuntimeException());

        if (user == null) {
            throw new IllegalArgumentException();
        }

        var entity = parseObject(file, File.class);
        entity.setUser(user);
        repository.save(entity);
    }

}
