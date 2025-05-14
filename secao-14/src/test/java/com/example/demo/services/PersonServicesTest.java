package com.example.demo.services;

import com.example.demo.data.dto.PersonDTO;
import com.example.demo.exception.RequiredObjectIsNullException;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.unitTests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    PersonServices personServices;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Person> mocked_list_person = input.mockEntityList();

        when(repository.findAll()).thenReturn(mocked_list_person);

        List<PersonDTO> people = personServices.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var person_1 = people.get(1);

        assertNotNull(person_1);
        assertNotNull(person_1.getId());
        assertNotNull(person_1.getLinks());

        assertTrue(person_1.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                && link.getType().equals("GET")
                )
        );

        assertTrue(person_1.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/person/all")
                                && link.getType().equals("GET")
                )
        );

        assertTrue(person_1.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/person")
                                && link.getType().equals("POST")
                )
        );

        assertTrue(person_1.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/person")
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(person_1.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/person/1")
                                && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test1", person_1.getAddress());
        assertEquals("First Name Test1", person_1.getFirstName());
        assertEquals("Last Name Test1", person_1.getLastName());
        assertEquals("Female", person_1.getGender());


    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = personServices.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                link -> link.getRel().value().equals("self")
                    && link.getHref().endsWith("/person/1")
                    && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                link -> link.getRel().value().equals("findAll")
                    && link.getHref().endsWith("/person/all")
                    && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                link -> link.getRel().value().equals("create")
                    && link.getHref().endsWith("/person")
                    && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                link -> link.getRel().value().equals("update")
                    && link.getHref().endsWith("/person")
                    && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                link -> link.getRel().value().equals("delete")
                    && link.getHref().endsWith("/person/1")
                    && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void create() {
        Person personToCreate = input.mockEntity(1);
        Person createdPerson = personToCreate;
        PersonDTO personDTO = input. mockDTO(1);

        when(repository.save(personToCreate)).thenReturn(createdPerson);

        var result = personServices.create(personDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/person/all")
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/person")
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/person")
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/person/1")
                                && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personServices.create(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person personToUpdate = input.mockEntity(1);
        Person updatedPerson = personToUpdate;
        PersonDTO personDTO = input.mockDTO(1);

        when(repository.findById(personDTO.getId())).thenReturn(Optional.of(personToUpdate));
        when(repository.save(personToUpdate)).thenReturn(updatedPerson);

        var result = personServices.update(personDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/person/all")
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/person")
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/person")
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/person/1")
                                && link.getType().equals("DELETE")
                )
        );

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personServices.update(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);

        when(repository.findById(person.getId())).thenReturn(Optional.of(person));

        personServices.delete(person.getId());

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }
}