package com.blibli.future.detroit.model.response;


import com.blibli.future.detroit.model.ParameterStatistic;
import com.blibli.future.detroit.model.dto.AgentPositionDto;
import com.blibli.future.detroit.model.dto.StatisticDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class StatisticDiagramIndividualResponse {
    private List<LocalDate> dates;
    private List<Float> scores;
    private List<StatisticDto> parameters;

    public StatisticDiagramIndividualResponse(List<LocalDate> dates, List<Float> scores, List<StatisticDto> parameters) {
        this.dates = dates;
        this.scores = scores;
        this.parameters = parameters;
    }

    @JsonIgnore
    public List<LocalDate> getDates() {
        return dates;
    }

    public List<String> getDateInISOFormat() {
        List<String> listOfString = new ArrayList<>();
        for(LocalDate date : this.dates) {
            listOfString.add(date.toString());
        }
        return listOfString;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public List<Float> getScores() {
        return scores;
    }

    public void setScores(List<Float> scores) {
        this.scores = scores;
    }

    public List<StatisticDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<StatisticDto> parameters) {
        this.parameters = parameters;
    }
}
