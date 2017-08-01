package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.ParameterStatistic;
import com.blibli.future.detroit.model.dto.AgentPositionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class StatisticDiagramResponseNew {
    private List<LocalDate> dates;
    private List<Float> scores;
    private List<AgentPositionDto> positions;

    public StatisticDiagramResponseNew(List<LocalDate> dates, List<Float> scores, List<AgentPositionDto> positions) {
        this.dates = dates;
        this.scores = scores;
        this.positions = positions;
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

    public List<AgentPositionDto> getPositions() {
        return positions;
    }

    public void setPositions(List<AgentPositionDto> positions) {
        this.positions = positions;
    }
}
