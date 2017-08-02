package com.blibli.future.detroit.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class StatisticDto implements Serializable {
    String name;
    List<Float> scores;
    List<StatisticDto> categories;

    public StatisticDto() {
    }

    public StatisticDto(String name, List<Float> scores) {
        this.name = name;
        this.scores = scores;
    }

    public StatisticDto(String name, List<Float> scores, List<StatisticDto> categories) {
        this.name = name;
        this.scores = scores;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Float> getScores() {
        return scores;
    }

    public void setScores(List<Float> scores) {
        this.scores = scores;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<StatisticDto> getCategories() {
        return categories;
    }

    public void setCategories(List<StatisticDto> categories) {
        this.categories = categories;
    }
}
