package com.blibli.future.detroit.model.request;

import com.blibli.future.detroit.model.DetailReview;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.dto.DetailReviewDto;

import java.io.Serializable;
import java.util.List;

public class NewReviewRequest implements Serializable {
    private Long id;
    private String casemgnt;
    private String interactionType;
    private String customerName;
    private String tlName;
    private Long parameter;
    private Long agent;
    private Long reviewer;
    private List<DetailReviewDto> detailReviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCasemgnt() {
        return casemgnt;
    }

    public void setCasemgnt(String casemgnt) {
        this.casemgnt = casemgnt;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTlName() {
        return tlName;
    }

    public void setTlName(String tlName) {
        this.tlName = tlName;
    }

    public Long getParameter() {
        return parameter;
    }

    public void setParameter(Long parameter) {
        this.parameter = parameter;
    }

    public Long getAgent() {
        return agent;
    }

    public void setAgent(Long agent) {
        this.agent = agent;
    }

    public Long getReviewer() {
        return reviewer;
    }

    public void setReviewer(Long reviewer) {
        this.reviewer = reviewer;
    }

    public List<DetailReviewDto> getDetailReviews() {
        return detailReviews;
    }

    public void setDetailReviews(List<DetailReviewDto> detailReviews) {
        this.detailReviews = detailReviews;
    }
}
