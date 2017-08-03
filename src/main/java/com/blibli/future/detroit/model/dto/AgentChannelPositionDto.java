package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.AgentChannel;

public class AgentChannelPositionDto {
    Long id;
    String name;

    public AgentChannelPositionDto() {
    }

    public AgentChannelPositionDto(AgentChannel agentChannel) {
        id = agentChannel.getId();
        name = agentChannel.getAgentPosition().getName() + "/" + agentChannel.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
