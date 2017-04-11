package com.blibli.future.detroit.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String casemgnt;
    private String interactionType;
    private String customerName;
    private String tlName;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User agent;
    @ManyToOne
    private User reviewer;
    @OneToMany
    private List<DetailReview> detailReview;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public List<DetailReview> getDetailReview() {
        return detailReview;
    }

    public void setDetailReview(List<DetailReview> detailReview) {
        this.detailReview = detailReview;
    }
}