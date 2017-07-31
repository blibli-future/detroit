package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.ParameterStatistic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class StatisticDiagramResponseNew {
    private List<LocalDate> dates;
    private List<Float> scores;
    private List<ParameterStatistic> parameter;

    public StatisticDiagramResponseNew(List<LocalDate> dates, List<Float> scores, List<ParameterStatistic> parameter) {
        this.dates = dates;
        this.scores = scores;
        this.parameter = parameter;
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

    public List<ParameterStatistic> getParameter() {
        return parameter;
    }

    public void setParameter(List<ParameterStatistic> parameter) {
        this.parameter = parameter;
    }

}
