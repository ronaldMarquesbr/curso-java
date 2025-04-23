package com.example.demo;

import com.example.demo.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll() {
        logger.info("Find all people");
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            people.add(person);
        }

        return people;
    }

    public Person findById(Long id) {
        logger.info("Finding one Person by id: " + id);

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setAddress("Belem - PA");
        person.setGender("Male");

        return person;
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

        return person;
    }

    public Person update(Person person) {
        logger.info("Updating person");

        return person;
    }

    public void delete(Long id) {
        logger.info("Deleting person");
    }
}
