package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.ParameterStatistic;

import java.io.Serializable;
import java.util.List;

public class AgentPositionDto implements Serializable {
    String name;
    List<ParameterStatistic> parameters;

    public AgentPositionDto(String name, List<ParameterStatistic> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterStatistic> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterStatistic> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentPositionDto that = (AgentPositionDto) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return parameters != null ? parameters.equals(that.parameters) : that.parameters == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
