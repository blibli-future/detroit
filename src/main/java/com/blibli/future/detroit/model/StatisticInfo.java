package com.blibli.future.detroit.model;

import java.util.List;

public class StatisticInfo {
    private Long totalAgent;
    private Long difAgent;
    private List<ParameterScore> parameterScores;
    private List<ReviewCount> reviewCounts;

    public Long getTotalAgent() {
        return totalAgent;
    }

    public void setTotalAgent(Long totalAgent) {
        this.totalAgent = totalAgent;
    }

    public Long getDifAgent() {
        return difAgent;
    }

    public void setDifAgent(Long difAgent) {
        this.difAgent = difAgent;
    }

    public List<ParameterScore> getParameterScores() {
        return parameterScores;
    }

    public void setParameterScores(List<ParameterScore> parameterScores) {
        this.parameterScores = parameterScores;
    }

    public List<ReviewCount> getReviewCounts() {
        return reviewCounts;
    }

    public void setReviewCounts(List<ReviewCount> reviewCounts) {
        this.reviewCounts = reviewCounts;
    }

    class ParameterScore {
        private String name;
        private Float score;
        private Float diff;

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

        public Float getDiff() {
            return diff;
        }

        public void setDiff(Float diff) {
            this.diff = diff;
        }
    }

    class ReviewCount {
        private String name;
        private List<String> reviewer;
        private List<Integer> value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getReviewer() {
            return reviewer;
        }

        public void setReviewer(List<String> reviewer) {
            this.reviewer = reviewer;
        }

        public List<Integer> getValue() {
            return value;
        }

        public void setValue(List<Integer> value) {
            this.value = value;
        }
    }
}
