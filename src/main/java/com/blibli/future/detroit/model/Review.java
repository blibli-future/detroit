package com.blibli.future.detroit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@SQLDelete(sql =
    "UPDATE review " +
    "SET deleted = true " +
    "WHERE id = ?")
@Where(clause = "deleted=false")
public class Review extends BaseModel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String casemgnt;
    private String interactionType;
    private String customerName;
    private String tlName;
    private Float score;
    @CreatedDate
    private LocalDateTime createdAt = new LocalDateTime();
    @ManyToOne
    @Where(clause = "deleted=false")
    private Parameter parameter;
    @ManyToOne
    @Where(clause = "deleted=false")
    private User agent;
    @ManyToOne
    @Where(clause = "deleted=false")
    private User reviewer;
    @OneToMany(mappedBy = "review")
    @Where(clause = "deleted=false")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<DetailReview> detailReview;
    @ManyToOne
    @Where(clause = "deleted=false")
    private CutOffHistory cutOffHistory;

    public CutOffHistory getCutOffHistory() {
        return cutOffHistory;
    }

    public void setCutOffHistory(CutOffHistory cutOffHistory) {
        this.cutOffHistory = cutOffHistory;
    }

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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtInISOFormat() {
        return this.createdAt.toString();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public List<DetailReview> getDetailReview() {
        return detailReview;
    }

    public void setDetailReview(List<DetailReview> detailReview) {
        this.detailReview = detailReview;
    }
}
