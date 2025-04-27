package com.example.demo.services;

import com.example.demo.exception.ResourceNotFoundException;
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

    public List<Person> findAll() {
        logger.info("Find all people");

        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one Person by id: " + id);

        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No record found for this ID")
        );
    }

    public Person mockPerson(int id) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Name " + id);
        person.setLastName("Lastname " + id);
        person.setAddress("Local " + id);
        person.setGender("Male");

        return person;
    }

    public Person create(Person person) {
        logger.info("Creating person");

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating person");

        Person entityToUpdate = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found of this ID"));

        entityToUpdate.setFirstName(person.getFirstName());
        entityToUpdate.setLastName(person.getLastName());
        entityToUpdate.setAddress(person.getAddress());
        entityToUpdate.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting person");
        Person entityToDelete = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found of this ID"));

        personRepository.delete(entityToDelete);
    }
}
