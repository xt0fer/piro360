package com.piro360.app.service;

import com.piro360.app.domain.Person;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Person}.
 */
public interface PersonService {
    /**
     * Save a person.
     *
     * @param person the entity to save.
     * @return the persisted entity.
     */
    Person save(Person person);

    /**
     * Partially updates a person.
     *
     * @param person the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Person> partialUpdate(Person person);

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    List<Person> findAll();

    /**
     * Get the "id" person.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Person> findOne(Long id);

    /**
     * Delete the "id" person.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
