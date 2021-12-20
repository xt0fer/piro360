package com.piro360.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Tags for attaching to videos.
 */
@ApiModel(description = "Tags for attaching to videos.")
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contents")
    private String contents;

    @Column(name = "location")
    private String location;

    @Column(name = "comment_date")
    private Instant commentDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tags", "owner" }, allowSetters = true)
    private Pirovideo pirovideo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return this.contents;
    }

    public Tag contents(String contents) {
        this.setContents(contents);
        return this;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLocation() {
        return this.location;
    }

    public Tag location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getCommentDate() {
        return this.commentDate;
    }

    public Tag commentDate(Instant commentDate) {
        this.setCommentDate(commentDate);
        return this;
    }

    public void setCommentDate(Instant commentDate) {
        this.commentDate = commentDate;
    }

    public Pirovideo getPirovideo() {
        return this.pirovideo;
    }

    public void setPirovideo(Pirovideo pirovideo) {
        this.pirovideo = pirovideo;
    }

    public Tag pirovideo(Pirovideo pirovideo) {
        this.setPirovideo(pirovideo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", contents='" + getContents() + "'" +
            ", location='" + getLocation() + "'" +
            ", commentDate='" + getCommentDate() + "'" +
            "}";
    }
}
