package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.Parameter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class StatisticDiagramResponseNew {
    private List<LocalDate> dates;
    private List<ParameterStatistic> parameter;

    public StatisticDiagramResponseNew() {
        this.dates = new ArrayList<>();
        this.parameter = new ArrayList<>();
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

    public List<ParameterStatistic> getParameter() {
        return parameter;
    }

    public void setParameter(List<ParameterStatistic> parameter) {
        this.parameter = parameter;
    }

    public void addParameter(String parameterName, Float score) {
        ParameterStatistic parameterStatistic = new ParameterStatistic();
        parameterStatistic.setName(parameterName);

    }

    class ParameterStatistic {
        protected String name;
        protected List<Float> scores;
        protected List<CategoryStatistic> category;

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

        public List<CategoryStatistic> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryStatistic> category) {
            this.category = category;
        }
    }

    class CategoryStatistic {
        protected String name;
        protected List<Float> scores;

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
}
