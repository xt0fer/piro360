package com.piro360.app.service;

import com.piro360.app.domain.Tag;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tag}.
 */
public interface TagService {
    /**
     * Save a tag.
     *
     * @param tag the entity to save.
     * @return the persisted entity.
     */
    Tag save(Tag tag);

    /**
     * Partially updates a tag.
     *
     * @param tag the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tag> partialUpdate(Tag tag);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<Tag> findAll();

    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tag> findOne(Long id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
