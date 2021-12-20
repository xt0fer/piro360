package com.piro360.app.web.rest;

import com.piro360.app.domain.Pirovideo;
import com.piro360.app.repository.PirovideoRepository;
import com.piro360.app.service.PirovideoService;
import com.piro360.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.piro360.app.domain.Pirovideo}.
 */
@RestController
@RequestMapping("/api")
public class PirovideoResource {

    private final Logger log = LoggerFactory.getLogger(PirovideoResource.class);

    private static final String ENTITY_NAME = "pirovideo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PirovideoService pirovideoService;

    private final PirovideoRepository pirovideoRepository;

    public PirovideoResource(PirovideoService pirovideoService, PirovideoRepository pirovideoRepository) {
        this.pirovideoService = pirovideoService;
        this.pirovideoRepository = pirovideoRepository;
    }

    /**
     * {@code POST  /pirovideos} : Create a new pirovideo.
     *
     * @param pirovideo the pirovideo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pirovideo, or with status {@code 400 (Bad Request)} if the pirovideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pirovideos")
    public ResponseEntity<Pirovideo> createPirovideo(@RequestBody Pirovideo pirovideo) throws URISyntaxException {
        log.debug("REST request to save Pirovideo : {}", pirovideo);
        if (pirovideo.getId() != null) {
            throw new BadRequestAlertException("A new pirovideo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pirovideo result = pirovideoService.save(pirovideo);
        return ResponseEntity
            .created(new URI("/api/pirovideos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pirovideos/:id} : Updates an existing pirovideo.
     *
     * @param id the id of the pirovideo to save.
     * @param pirovideo the pirovideo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pirovideo,
     * or with status {@code 400 (Bad Request)} if the pirovideo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pirovideo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pirovideos/{id}")
    public ResponseEntity<Pirovideo> updatePirovideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Pirovideo pirovideo
    ) throws URISyntaxException {
        log.debug("REST request to update Pirovideo : {}, {}", id, pirovideo);
        if (pirovideo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pirovideo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pirovideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pirovideo result = pirovideoService.save(pirovideo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pirovideo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pirovideos/:id} : Partial updates given fields of an existing pirovideo, field will ignore if it is null
     *
     * @param id the id of the pirovideo to save.
     * @param pirovideo the pirovideo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pirovideo,
     * or with status {@code 400 (Bad Request)} if the pirovideo is not valid,
     * or with status {@code 404 (Not Found)} if the pirovideo is not found,
     * or with status {@code 500 (Internal Server Error)} if the pirovideo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pirovideos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pirovideo> partialUpdatePirovideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Pirovideo pirovideo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pirovideo partially : {}, {}", id, pirovideo);
        if (pirovideo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pirovideo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pirovideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pirovideo> result = pirovideoService.partialUpdate(pirovideo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pirovideo.getId().toString())
        );
    }

    /**
     * {@code GET  /pirovideos} : get all the pirovideos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pirovideos in body.
     */
    @GetMapping("/pirovideos")
    public List<Pirovideo> getAllPirovideos() {
        log.debug("REST request to get all Pirovideos");
        return pirovideoService.findAll();
    }

    /**
     * {@code GET  /pirovideos/:id} : get the "id" pirovideo.
     *
     * @param id the id of the pirovideo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pirovideo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pirovideos/{id}")
    public ResponseEntity<Pirovideo> getPirovideo(@PathVariable Long id) {
        log.debug("REST request to get Pirovideo : {}", id);
        Optional<Pirovideo> pirovideo = pirovideoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pirovideo);
    }

    /**
     * {@code DELETE  /pirovideos/:id} : delete the "id" pirovideo.
     *
     * @param id the id of the pirovideo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pirovideos/{id}")
    public ResponseEntity<Void> deletePirovideo(@PathVariable Long id) {
        log.debug("REST request to delete Pirovideo : {}", id);
        pirovideoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
