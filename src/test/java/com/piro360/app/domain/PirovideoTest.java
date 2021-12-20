package com.piro360.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.piro360.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PirovideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pirovideo.class);
        Pirovideo pirovideo1 = new Pirovideo();
        pirovideo1.setId(1L);
        Pirovideo pirovideo2 = new Pirovideo();
        pirovideo2.setId(pirovideo1.getId());
        assertThat(pirovideo1).isEqualTo(pirovideo2);
        pirovideo2.setId(2L);
        assertThat(pirovideo1).isNotEqualTo(pirovideo2);
        pirovideo1.setId(null);
        assertThat(pirovideo1).isNotEqualTo(pirovideo2);
    }
}
