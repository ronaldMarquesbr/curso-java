package com.example.demo.services;

import com.example.demo.controllers.PersonController;
import com.example.demo.data.dto.PersonDTO;
import com.example.demo.exception.RequiredObjectIsNullException;
import com.example.demo.exception.ResourceNotFoundException;
import static com.example.demo.mapper.ObjectMapper.parseListObjects;
import static com.example.demo.mapper.ObjectMapper.parseObject;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    @Autowired
    private PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<PersonDTO> findAll() {
        logger.info("Find all people");

        var people = parseListObjects(personRepository.findAll(), PersonDTO.class);

        people.forEach(this::addHateoasLinks);

        return people;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person by id: " + id);

        var dto = personRepository.findById(id)
                .map(it -> parseObject(it, PersonDTO.class))
                .orElseThrow(
                () -> new ResourceNotFoundException("No record found for this ID"));

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating person");

        Person entityToSave = parseObject(person, Person.class);

        var dto = parseObject(personRepository.save(entityToSave), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating person with id: " + person.getId());

        Person entityToUpdate = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found of this ID"));

        entityToUpdate.setFirstName(person.getFirstName());
        entityToUpdate.setLastName(person.getLastName());
        entityToUpdate.setAddress(person.getAddress());
        entityToUpdate.setGender(person.getGender());

        var dto = parseObject(personRepository.save(entityToUpdate), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting person");
        Person entityToDelete = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found of this ID"));

        personRepository.delete(entityToDelete);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
