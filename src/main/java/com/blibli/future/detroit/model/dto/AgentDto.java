package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.User;

public class AgentDto extends UserDto {

    String teamLeader;
    String agentChannel;
    String agentPosition;

    public AgentDto() {}

    public AgentDto(User user) {
        super(user);
        this.teamLeader = user.getTeamLeader();
        this.agentChannel = user.getAgentChannel().getName();
        this.agentPosition = user.getAgentPosition().getName();
    }


    // AUTO GENERATED

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getAgentChannel() {
        return agentChannel;
    }

    public void setAgentChannel(String agentChannel) {
        this.agentChannel = agentChannel;
    }

    public String getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(String agentPosition) {
        this.agentPosition = agentPosition;
    }
}
