package com.piro360.app.repository;

import com.piro360.app.domain.Pirovideo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pirovideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PirovideoRepository extends JpaRepository<Pirovideo, Long> {}
