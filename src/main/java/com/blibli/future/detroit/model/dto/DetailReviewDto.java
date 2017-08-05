package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.Category;

import java.io.Serializable;

public class DetailReviewDto implements Serializable {
    Long id;
    Float score;
    String note;
    Long category;

    public DetailReviewDto() {
    }

    public DetailReviewDto(Float score, String note, Long category) {
        this.score = score;
        this.note = note;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
