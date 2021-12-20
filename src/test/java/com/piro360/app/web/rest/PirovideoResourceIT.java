package com.piro360.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.piro360.app.IntegrationTest;
import com.piro360.app.domain.Pirovideo;
import com.piro360.app.repository.PirovideoRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PirovideoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PirovideoResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_VIDURL = "AAAAAAAAAA";
    private static final String UPDATED_VIDURL = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECORD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pirovideos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PirovideoRepository pirovideoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPirovideoMockMvc;

    private Pirovideo pirovideo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pirovideo createEntity(EntityManager em) {
        Pirovideo pirovideo = new Pirovideo()
            .title(DEFAULT_TITLE)
            .notes(DEFAULT_NOTES)
            .location(DEFAULT_LOCATION)
            .vidurl(DEFAULT_VIDURL)
            .recordDate(DEFAULT_RECORD_DATE);
        return pirovideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pirovideo createUpdatedEntity(EntityManager em) {
        Pirovideo pirovideo = new Pirovideo()
            .title(UPDATED_TITLE)
            .notes(UPDATED_NOTES)
            .location(UPDATED_LOCATION)
            .vidurl(UPDATED_VIDURL)
            .recordDate(UPDATED_RECORD_DATE);
        return pirovideo;
    }

    @BeforeEach
    public void initTest() {
        pirovideo = createEntity(em);
    }

    @Test
    @Transactional
    void createPirovideo() throws Exception {
        int databaseSizeBeforeCreate = pirovideoRepository.findAll().size();
        // Create the Pirovideo
        restPirovideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pirovideo)))
            .andExpect(status().isCreated());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeCreate + 1);
        Pirovideo testPirovideo = pirovideoList.get(pirovideoList.size() - 1);
        assertThat(testPirovideo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPirovideo.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPirovideo.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPirovideo.getVidurl()).isEqualTo(DEFAULT_VIDURL);
        assertThat(testPirovideo.getRecordDate()).isEqualTo(DEFAULT_RECORD_DATE);
    }

    @Test
    @Transactional
    void createPirovideoWithExistingId() throws Exception {
        // Create the Pirovideo with an existing ID
        pirovideo.setId(1L);

        int databaseSizeBeforeCreate = pirovideoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPirovideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pirovideo)))
            .andExpect(status().isBadRequest());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPirovideos() throws Exception {
        // Initialize the database
        pirovideoRepository.saveAndFlush(pirovideo);

        // Get all the pirovideoList
        restPirovideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pirovideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].vidurl").value(hasItem(DEFAULT_VIDURL)))
            .andExpect(jsonPath("$.[*].recordDate").value(hasItem(DEFAULT_RECORD_DATE.toString())));
    }

    @Test
    @Transactional
    void getPirovideo() throws Exception {
        // Initialize the database
        pirovideoRepository.saveAndFlush(pirovideo);

        // Get the pirovideo
        restPirovideoMockMvc
            .perform(get(ENTITY_API_URL_ID, pirovideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pirovideo.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.vidurl").value(DEFAULT_VIDURL))
            .andExpect(jsonPath("$.recordDate").value(DEFAULT_RECORD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPirovideo() throws Exception {
        // Get the pirovideo
        restPirovideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPirovideo() throws Exception {
        // Initialize the database
        pirovideoRepository.saveAndFlush(pirovideo);

        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();

        // Update the pirovideo
        Pirovideo updatedPirovideo = pirovideoRepository.findById(pirovideo.getId()).get();
        // Disconnect from session so that the updates on updatedPirovideo are not directly saved in db
        em.detach(updatedPirovideo);
        updatedPirovideo
            .title(UPDATED_TITLE)
            .notes(UPDATED_NOTES)
            .location(UPDATED_LOCATION)
            .vidurl(UPDATED_VIDURL)
            .recordDate(UPDATED_RECORD_DATE);

        restPirovideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPirovideo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPirovideo))
            )
            .andExpect(status().isOk());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
        Pirovideo testPirovideo = pirovideoList.get(pirovideoList.size() - 1);
        assertThat(testPirovideo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPirovideo.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPirovideo.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPirovideo.getVidurl()).isEqualTo(UPDATED_VIDURL);
        assertThat(testPirovideo.getRecordDate()).isEqualTo(UPDATED_RECORD_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPirovideo() throws Exception {
        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();
        pirovideo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPirovideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pirovideo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pirovideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPirovideo() throws Exception {
        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();
        pirovideo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPirovideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pirovideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPirovideo() throws Exception {
        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();
        pirovideo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPirovideoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pirovideo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePirovideoWithPatch() throws Exception {
        // Initialize the database
        pirovideoRepository.saveAndFlush(pirovideo);

        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();

        // Update the pirovideo using partial update
        Pirovideo partialUpdatedPirovideo = new Pirovideo();
        partialUpdatedPirovideo.setId(pirovideo.getId());

        partialUpdatedPirovideo.location(UPDATED_LOCATION).recordDate(UPDATED_RECORD_DATE);

        restPirovideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPirovideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPirovideo))
            )
            .andExpect(status().isOk());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
        Pirovideo testPirovideo = pirovideoList.get(pirovideoList.size() - 1);
        assertThat(testPirovideo.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPirovideo.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPirovideo.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPirovideo.getVidurl()).isEqualTo(DEFAULT_VIDURL);
        assertThat(testPirovideo.getRecordDate()).isEqualTo(UPDATED_RECORD_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePirovideoWithPatch() throws Exception {
        // Initialize the database
        pirovideoRepository.saveAndFlush(pirovideo);

        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();

        // Update the pirovideo using partial update
        Pirovideo partialUpdatedPirovideo = new Pirovideo();
        partialUpdatedPirovideo.setId(pirovideo.getId());

        partialUpdatedPirovideo
            .title(UPDATED_TITLE)
            .notes(UPDATED_NOTES)
            .location(UPDATED_LOCATION)
            .vidurl(UPDATED_VIDURL)
            .recordDate(UPDATED_RECORD_DATE);

        restPirovideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPirovideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPirovideo))
            )
            .andExpect(status().isOk());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
        Pirovideo testPirovideo = pirovideoList.get(pirovideoList.size() - 1);
        assertThat(testPirovideo.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPirovideo.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPirovideo.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPirovideo.getVidurl()).isEqualTo(UPDATED_VIDURL);
        assertThat(testPirovideo.getRecordDate()).isEqualTo(UPDATED_RECORD_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPirovideo() throws Exception {
        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();
        pirovideo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPirovideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pirovideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pirovideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPirovideo() throws Exception {
        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();
        pirovideo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPirovideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pirovideo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPirovideo() throws Exception {
        int databaseSizeBeforeUpdate = pirovideoRepository.findAll().size();
        pirovideo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPirovideoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pirovideo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pirovideo in the database
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePirovideo() throws Exception {
        // Initialize the database
        pirovideoRepository.saveAndFlush(pirovideo);

        int databaseSizeBeforeDelete = pirovideoRepository.findAll().size();

        // Delete the pirovideo
        restPirovideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pirovideo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pirovideo> pirovideoList = pirovideoRepository.findAll();
        assertThat(pirovideoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
