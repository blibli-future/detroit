package com.blibli.future.detroit.model.response;

import java.util.ArrayList;
import java.util.List;

public class AgentOverviewResponse {
    private String role;
    private List<AgentOverview> agents;

    public AgentOverviewResponse() {
        this.role = new String();
        this.agents = new ArrayList<>();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<AgentOverview> getAgents() {
        return agents;
    }

    public void setAgents(List<AgentOverview> agents) {
        this.agents = agents;
    }

    public void addAgents(Long idAgent, Long idParameter, String nickname, String email, String position, String channel, Integer reviewCount) {
        this.agents.add(new AgentOverview(idAgent, idParameter, nickname, email, position, channel, reviewCount));
    }

    class AgentOverview {
        private Long idAgent;
        private Long idParameter;
        private String nickname;
        private String email;
        private String position;
        private String channel;
        private Integer reviewCount;

        public AgentOverview(Long idAgent, Long idParameter, String nickname, String email, String position, String channel, Integer reviewCount) {
            this.idAgent = idAgent;
            this.idParameter = idParameter;
            this.nickname = nickname;
            this.email = email;
            this.position = position;
            this.channel = channel;
            this.reviewCount = reviewCount;
        }

        public Long getIdAgent() {
            return idAgent;
        }

        public void setIdAgent(Long idAgent) {
            this.idAgent = idAgent;
        }

        public Long getIdParameter() {
            return idParameter;
        }

        public void setIdParameter(Long idParameter) {
            this.idParameter = idParameter;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public Integer getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(Integer reviewCount) {
            this.reviewCount = reviewCount;
        }
    }
}
