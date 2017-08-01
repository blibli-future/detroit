package com.blibli.future.detroit.model;

import java.util.List;

public class ParameterStatistic {
    protected String name;
    protected List<Float> scores;

    public ParameterStatistic(String name, List<Float> scores) {
        this.name = name;
        this.scores = scores;
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
}
