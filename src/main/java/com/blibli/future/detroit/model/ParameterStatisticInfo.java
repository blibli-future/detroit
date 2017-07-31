package com.blibli.future.detroit.model;

import java.math.BigDecimal;
import java.util.List;

public class ParameterStatisticInfo {
    protected String name;
    protected Float score;
    protected BigDecimal diffScore;

    public ParameterStatisticInfo(String name, Float score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public BigDecimal getDiffScore() {
        return diffScore;
    }

    public void setDiffScore(Float diffScore) {
        this.diffScore = new BigDecimal(diffScore.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
