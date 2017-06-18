package com.blibli.future.detroit.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticDiagramResponse {
    private String parameter;
    private List<StatisticValue> value;
    private HashMap<String, List<StatisticValue>> category;

    public StatisticDiagramResponse() {
        this.value = new ArrayList<>();
        this.category = new HashMap<>();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public List<StatisticValue> getValue() {
        return value;
    }

    public void setValue(List<StatisticValue> value) {
        this.value = value;
    }

    public void addValue(Float value, LocalDate date) {
        StatisticValue statisticValue = new StatisticValue();
        statisticValue.setScore(value);
        statisticValue.setDate(date);
        this.value.add(statisticValue);
    }

    public HashMap<String, List<StatisticValue>> getCategory() {
        return category;
    }

    public void setCategory(HashMap<String, List<StatisticValue>> category) {
        this.category = category;
    }

    public void addCategory(String name, Float value, LocalDate date) {
        if(this.category.get(name) == null) {
            StatisticValue statisticValue = new StatisticValue();
            statisticValue.setScore(value);
            statisticValue.setDate(date);
            List<StatisticValue> statisticValues = new ArrayList<>();
            statisticValues.add(statisticValue);
            this.category.put(name, statisticValues);
        } else {
            StatisticValue statisticValue = new StatisticValue();
            statisticValue.setScore(value);
            statisticValue.setDate(date);
            List<StatisticValue> statisticValues = this.category.get(name);
            statisticValues.add(statisticValue);
            this.category.put(name, statisticValues);
        }
    }

    class StatisticValue {
        protected LocalDate date;
        protected Float score;

        @JsonIgnore
        public LocalDate getDate() {
            return date;
        }

        public String getDateInISOFormat() {
            return this.date.toString();
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Float getScore() {
            return score;
        }

        public void setScore(Float score) {
            this.score = score;
        }
    }
}
