package com.blibli.future.detroit.model.request;

import com.blibli.future.detroit.model.DetailReview;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.User;

import java.io.Serializable;
import java.util.List;

public class NewReviewRequest implements Serializable {
    private Long id;
    private String casemgnt;
    private String interactionType;
    private String customerName;
    private String tlName;
    private Parameter parameter;
    private User agent;
    private User reviewer;
    private List<DetailReview> detailReviews;

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

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public List<DetailReview> getDetailReviews() {
        return detailReviews;
    }

    public void setDetailReviews(List<DetailReview> detailReviews) {
        this.detailReviews = detailReviews;
    }
}
