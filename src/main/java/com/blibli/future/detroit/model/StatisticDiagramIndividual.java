package com.blibli.future.detroit.model;

import java.io.Serializable;
import java.util.List;

public class StatisticDiagramIndividual implements Serializable {
    private String name;
    private List<Float> score;
    private List<CutOffHistory> cutOffHistories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Float> getScore() {
        return score;
    }

    public void setScore(List<Float> score) {
        this.score = score;
    }

    public List<CutOffHistory> getCutOffHistories() {
        return cutOffHistories;
    }

    public void setCutOffHistories(List<CutOffHistory> cutOffHistories) {
        this.cutOffHistories = cutOffHistories;
    }
}
