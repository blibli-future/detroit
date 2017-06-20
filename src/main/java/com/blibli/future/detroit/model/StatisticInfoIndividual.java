package com.blibli.future.detroit.model;

import java.io.Serializable;
import java.util.HashMap;

public class StatisticInfoIndividual implements Serializable {
    private Float totalScore;
    private HashMap<String, Float> parameterScore;

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public HashMap<String, Float> getParameterScore() {
        return parameterScore;
    }

    public void setParameterScore(HashMap<String, Float> parameterScore) {
        this.parameterScore = parameterScore;
    }
}
