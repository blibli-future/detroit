package com.blibli.future.detroit.model;

import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticInfo implements Serializable {
    private Integer totalAgent;
    private List<ParameterScore> parameterScores;
    private HashMap<String, List<ReviewCount>> reviewCounts;

    public StatisticInfo() {
        this.parameterScores = new ArrayList<>();
        this.reviewCounts = new HashMap<>();
    }

    public Integer getTotalAgent() {
        return totalAgent;
    }

    public void setTotalAgent(Integer totalAgent) {
        this.totalAgent = totalAgent;
    }

    public List<ParameterScore> getParameterScores() {
        return parameterScores;
    }

    public void setParameterScores(List<ParameterScore> parameterScores) {
        this.parameterScores = parameterScores;
    }

    public void addParameterScores(String name, Float score) {
        ParameterScore parameterScore = new ParameterScore();
        parameterScore.setName(name);
        parameterScore.setScore(score);
        this.parameterScores.add(parameterScore);
    }

    public HashMap<String, List<ReviewCount>> getReviewCounts() {
        return reviewCounts;
    }

    public void setReviewCounts(HashMap<String, List<ReviewCount>> reviewCounts) {
        this.reviewCounts = reviewCounts;
    }

    public void addReviewCounts(String name, String reviewer, Integer value) {
        if(this.reviewCounts.get(name) == null) {
            ReviewCount reviewCount = new ReviewCount();
            reviewCount.setReviewer(reviewer);
            reviewCount.setValue(value);
            List<ReviewCount> reviewCountList = new ArrayList<>();
            reviewCountList.add(reviewCount);
            this.reviewCounts.put(name, reviewCountList);
        } else {
            ReviewCount reviewCount = new ReviewCount();
            reviewCount.setReviewer(reviewer);
            reviewCount.setValue(value);
            List<ReviewCount> reviewCountList = this.reviewCounts.get(name);
            reviewCountList.add(reviewCount);
            this.reviewCounts.put(name, reviewCountList);
        }
    }

    class ParameterScore {
        private String name;
        private Float score;

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
    }

    class ReviewCount {
        private String reviewer;
        private Integer value;

        public String getReviewer() {
            return reviewer;
        }

        public void setReviewer(String reviewer) {
            this.reviewer = reviewer;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
