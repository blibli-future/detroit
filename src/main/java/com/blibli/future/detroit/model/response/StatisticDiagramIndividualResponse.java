package com.blibli.future.detroit.model.response;


import org.joda.time.LocalDate;

public class StatisticDiagramIndividualResponse {
    private LocalDate date;
    private Float score;

    public StatisticDiagramIndividualResponse(LocalDate date, Float score) {
        this.date = date;
        this.score = score;
    }

    public String getDate() {
        return this.date.toString();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
