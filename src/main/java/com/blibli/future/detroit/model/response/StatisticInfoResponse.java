package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.ParameterStatisticInfo;

import java.math.BigDecimal;
import java.util.List;

public class StatisticInfoResponse {
    private Integer totalAgent;
    private Float finalScore;
    private BigDecimal diffFinalScore;
    private List<ParameterStatisticInfo> parameters;

    public StatisticInfoResponse(Integer totalAgent, Float finalScore, Float diffFinalScore, List<ParameterStatisticInfo> parameters) {
        this.totalAgent = totalAgent;
        this.finalScore = finalScore;
        this.diffFinalScore = new BigDecimal(diffFinalScore.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.parameters = parameters;
    }

    public Integer getTotalAgent() {
        return totalAgent;
    }

    public void setTotalAgent(Integer totalAgent) {
        this.totalAgent = totalAgent;
    }

    public Float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Float finalScore) {
        this.finalScore = finalScore;
    }

    public BigDecimal getDiffFinalScore() {
        return diffFinalScore;
    }

    public void setDiffFinalScore(Float diffFinalScore) {
        this.diffFinalScore = new BigDecimal(diffFinalScore.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<ParameterStatisticInfo> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterStatisticInfo> parameters) {
        this.parameters = parameters;
    }
}
