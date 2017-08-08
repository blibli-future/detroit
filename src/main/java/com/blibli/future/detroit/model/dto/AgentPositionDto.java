package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.AgentPosition;
import com.blibli.future.detroit.model.ParameterStatistic;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class AgentPositionDto implements Serializable {
    Long id;
    String name;
    List<ParameterStatistic> parameters;
    List<AgentChannelDto> agentChannels;

    public AgentPositionDto() {}

    public AgentPositionDto(String name, List<ParameterStatistic> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public AgentPositionDto(AgentPosition agentPosition) {
        this.id = agentPosition.getId();
        this.name = agentPosition.getName();
        this.agentChannels = agentPosition.getAgentChannels().stream()
                                          .map(AgentChannelDto::new)
                                          .collect(Collectors.toList());
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

    public List<AgentChannelDto> getAgentChannels() {
        return agentChannels;
    }

    public void setAgentChannels(List<AgentChannelDto> agentChannels) {
        this.agentChannels = agentChannels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentPositionDto that = (AgentPositionDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        return agentChannels != null ? agentChannels.equals(that.agentChannels) : that.agentChannels == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (agentChannels != null ? agentChannels.hashCode() : 0);
        return result;
    }
}
