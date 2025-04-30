package com.example.demo.services;

import com.example.demo.data.dto.PersonDTO;
import com.example.demo.exception.ResourceNotFoundException;
import static com.example.demo.mapper.ObjectMapper.parseListObjects;
import static com.example.demo.mapper.ObjectMapper.parseObject;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    @Autowired
    private PersonRepository personRepository;

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<PersonDTO> findAll() {
        logger.info("Find all people");

        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person by id: " + id);

        return personRepository.findById(id)
                .map(it -> parseObject(it, PersonDTO.class))
                .orElseThrow(
                () -> new ResourceNotFoundException("No record found for this ID"));
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating person");

        Person entityToSave = parseObject(person, Person.class);

        return parseObject(personRepository.save(entityToSave), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating person");

        Person entityToUpdate = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found of this ID"));

        entityToUpdate.setFirstName(person.getFirstName());
        entityToUpdate.setLastName(person.getLastName());
        entityToUpdate.setAddress(person.getAddress());
        entityToUpdate.setGender(person.getGender());

        return parseObject(personRepository.save(entityToUpdate), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting person");
        Person entityToDelete = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found of this ID"));

        personRepository.delete(entityToDelete);
    }
}
