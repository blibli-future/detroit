package com.blibli.future.detroit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class CutOffHistory implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate beginCutOff;
    private LocalDate endCutOff;
    @OneToMany
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public LocalDate getBeginCutOff() {
        return beginCutOff;
    }

    public void setBeginCutOff(LocalDate beginCutOff) {
        this.beginCutOff = beginCutOff;
    }

    @JsonIgnore
    public LocalDate getEndCutOff() {
        return endCutOff;
    }

    public void setEndCutOff(LocalDate endCutOff) {
        this.endCutOff = endCutOff;
    }

    public String getBeginInISOFormat() {
        return this.beginCutOff.toString();
    }

    public String getEndInISOFormat() {
        if(this.endCutOff == null) {
            return null;
        }
        return this.endCutOff.toString();
    }
}
