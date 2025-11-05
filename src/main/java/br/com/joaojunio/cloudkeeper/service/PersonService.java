package br.com.joaojunio.cloudkeeper.service;

import br.com.joaojunio.cloudkeeper.data.dto.person.PersonDTO;
import br.com.joaojunio.cloudkeeper.exceptions.NotFoundException;
import br.com.joaojunio.cloudkeeper.model.Person;
import br.com.joaojunio.cloudkeeper.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseObject;
import static br.com.joaojunio.cloudkeeper.mapper.ObjectMapper.parseListObjects;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    FolderStructureService folderStructureService;

    public List<PersonDTO> findAll() {

        logger.info("Finding all User");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {

        logger.info("Finding one User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));

        var dto = parseObject(entity, PersonDTO.class);
        return dto;
    }

    public PersonDTO findByUserTestFile() {

        var entity = repository.findById(2L)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + 2L));

        var dto = parseObject(entity, PersonDTO.class);
        return dto;
    }

    public PersonDTO create(PersonDTO user) {

        logger.info("Creating new User");

        var entity = parseObject(user, Person.class);
        var entitySaved = repository.save(entity);

        folderStructureService.createUserFolderStructure(parseObject(entitySaved, PersonDTO.class));

        return parseObject(entitySaved, PersonDTO.class);
    }

    public PersonDTO update(PersonDTO user) {

        logger.info("Updating a User");

        var entity = repository.findById(user.getId())
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + user.getId()));
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setEnabled(user.getEnabled());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));
        repository.delete(entity);
    }
}
