package com.piro360.app.service.impl;

import com.piro360.app.domain.Pirovideo;
import com.piro360.app.repository.PirovideoRepository;
import com.piro360.app.service.PirovideoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pirovideo}.
 */
@Service
@Transactional
public class PirovideoServiceImpl implements PirovideoService {

    private final Logger log = LoggerFactory.getLogger(PirovideoServiceImpl.class);

    private final PirovideoRepository pirovideoRepository;

    public PirovideoServiceImpl(PirovideoRepository pirovideoRepository) {
        this.pirovideoRepository = pirovideoRepository;
    }

    @Override
    public Pirovideo save(Pirovideo pirovideo) {
        log.debug("Request to save Pirovideo : {}", pirovideo);
        return pirovideoRepository.save(pirovideo);
    }

    @Override
    public Optional<Pirovideo> partialUpdate(Pirovideo pirovideo) {
        log.debug("Request to partially update Pirovideo : {}", pirovideo);

        return pirovideoRepository
            .findById(pirovideo.getId())
            .map(existingPirovideo -> {
                if (pirovideo.getTitle() != null) {
                    existingPirovideo.setTitle(pirovideo.getTitle());
                }
                if (pirovideo.getNotes() != null) {
                    existingPirovideo.setNotes(pirovideo.getNotes());
                }
                if (pirovideo.getLocation() != null) {
                    existingPirovideo.setLocation(pirovideo.getLocation());
                }
                if (pirovideo.getVidurl() != null) {
                    existingPirovideo.setVidurl(pirovideo.getVidurl());
                }
                if (pirovideo.getRecordDate() != null) {
                    existingPirovideo.setRecordDate(pirovideo.getRecordDate());
                }

                return existingPirovideo;
            })
            .map(pirovideoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pirovideo> findAll() {
        log.debug("Request to get all Pirovideos");
        return pirovideoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pirovideo> findOne(Long id) {
        log.debug("Request to get Pirovideo : {}", id);
        return pirovideoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pirovideo : {}", id);
        pirovideoRepository.deleteById(id);
    }
}
