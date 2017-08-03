package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.AgentChannel;

public class AgentChannelDto {
    Long id;
    String name;
    String position;

    public AgentChannelDto() {
    }

    public AgentChannelDto(AgentChannel agentChannel) {
        id = agentChannel.getId();
        name = agentChannel.getName();
        position = agentChannel.getAgentPosition().getName();
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
