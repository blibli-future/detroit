package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.User;

import java.util.HashMap;
import java.util.List;

public class AgentOverviewResponse {
    private Long idAgent;
    private String nickname;
    private String email;
    private String position;
    private String channel;
    private Integer reviewCount;

    public AgentOverviewResponse() {
    }

    public AgentOverviewResponse(Long idAgent, String nickname, String email, String position, String channel, Integer reviewCount) {
        this.idAgent = idAgent;
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
