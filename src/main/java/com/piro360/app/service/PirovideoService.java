package com.piro360.app.service;

import com.piro360.app.domain.Pirovideo;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pirovideo}.
 */
public interface PirovideoService {
    /**
     * Save a pirovideo.
     *
     * @param pirovideo the entity to save.
     * @return the persisted entity.
     */
    Pirovideo save(Pirovideo pirovideo);

    /**
     * Partially updates a pirovideo.
     *
     * @param pirovideo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pirovideo> partialUpdate(Pirovideo pirovideo);

    /**
     * Get all the pirovideos.
     *
     * @return the list of entities.
     */
    List<Pirovideo> findAll();

    /**
     * Get the "id" pirovideo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pirovideo> findOne(Long id);

    /**
     * Delete the "id" pirovideo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
