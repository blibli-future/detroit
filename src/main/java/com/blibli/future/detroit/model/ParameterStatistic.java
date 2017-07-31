package com.blibli.future.detroit.model;

import java.util.List;

public class ParameterStatistic {
    protected String name;
    protected List<Float> scores;
    protected List<CategoryStatistic> category;

    public ParameterStatistic(String name, List<Float> scores, List<CategoryStatistic> category) {
        this.name = name;
        this.scores = scores;
        this.category = category;
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

    public List<CategoryStatistic> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryStatistic> category) {
        this.category = category;
    }
}
