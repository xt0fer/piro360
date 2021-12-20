package com.piro360.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pirovideo.
 */
@Entity
@Table(name = "pirovideo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pirovideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "notes")
    private String notes;

    @Column(name = "location")
    private String location;

    @Column(name = "vidurl")
    private String vidurl;

    @Column(name = "record_date")
    private Instant recordDate;

    @OneToMany(mappedBy = "pirovideo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pirovideo" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "pirovideos" }, allowSetters = true)
    private Person owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pirovideo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Pirovideo title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return this.notes;
    }

    public Pirovideo notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return this.location;
    }

    public Pirovideo location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVidurl() {
        return this.vidurl;
    }

    public Pirovideo vidurl(String vidurl) {
        this.setVidurl(vidurl);
        return this;
    }

    public void setVidurl(String vidurl) {
        this.vidurl = vidurl;
    }

    public Instant getRecordDate() {
        return this.recordDate;
    }

    public Pirovideo recordDate(Instant recordDate) {
        this.setRecordDate(recordDate);
        return this;
    }

    public void setRecordDate(Instant recordDate) {
        this.recordDate = recordDate;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (this.tags != null) {
            this.tags.forEach(i -> i.setPirovideo(null));
        }
        if (tags != null) {
            tags.forEach(i -> i.setPirovideo(this));
        }
        this.tags = tags;
    }

    public Pirovideo tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Pirovideo addTag(Tag tag) {
        this.tags.add(tag);
        tag.setPirovideo(this);
        return this;
    }

    public Pirovideo removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.setPirovideo(null);
        return this;
    }

    public Person getOwner() {
        return this.owner;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    public Pirovideo owner(Person person) {
        this.setOwner(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pirovideo)) {
            return false;
        }
        return id != null && id.equals(((Pirovideo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pirovideo{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", notes='" + getNotes() + "'" +
            ", location='" + getLocation() + "'" +
            ", vidurl='" + getVidurl() + "'" +
            ", recordDate='" + getRecordDate() + "'" +
            "}";
    }
}
