package com.blibli.future.detroit.model.response;

import java.time.LocalDate;

public class AgentReviewNoteResponse {
    private String category;
    private Float score;
    private String note;

    public AgentReviewNoteResponse(String category, Float score, String note) {
        this.category = category;
        this.score = score;
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
