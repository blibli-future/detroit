package com.blibli.future.detroit.model.dto;

import java.io.Serializable;

public class ReviewHistoryDto implements Serializable {
    Long id;
    String emailAgent;
    String agentFullname;
    String position;
    String channel;
    String parameter;
    Float score;
    String reviewDate;

    public ReviewHistoryDto() {
    }

    public ReviewHistoryDto(Long id, String emailAgent, String agentFullname, String position, String channel, String parameter, Float score, String reviewDate) {
        this.id = id;
        this.emailAgent = emailAgent;
        this.agentFullname = agentFullname;
        this.position = position;
        this.channel = channel;
        this.parameter = parameter;
        this.score = score;
        this.reviewDate = reviewDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAgent() {
        return emailAgent;
    }

    public void setEmailAgent(String emailAgent) {
        this.emailAgent = emailAgent;
    }

    public String getAgentFullname() {
        return agentFullname;
    }

    public void setAgentFullname(String agentFullname) {
        this.agentFullname = agentFullname;
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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
