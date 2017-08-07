package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.model.DetailReview;
import com.blibli.future.detroit.model.Review;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OneReviewResponse {
    private Long id;
    private String casemgnt;
    private String interactionType;
    private String customerName;
    private String tlName;
    private Float score;
    private Long parameter;
    private Long agent;
    private Long reviewer;
    private Long cutOff;
    private List<SingleDetailReview> detailReviews;

    public OneReviewResponse(Review review) {
        this.id = review.getId();
        this.casemgnt = review.getCasemgnt();
        this.interactionType = review.getInteractionType();
        this.customerName = review.getCustomerName();
        this.tlName = review.getTlName();
        this.score = review.getScore();
        this.parameter = review.getParameter().getId();
        this.agent = review.getAgent().getId();
        this.reviewer = review.getReviewer().getId();
        this.cutOff = review.getCutOffHistory().getId();
        this.detailReviews = new ArrayList<>();
        for (DetailReview detailReview : review.getDetailReview()) {
            this.detailReviews.add(new SingleDetailReview(detailReview));
        }
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

    public Long getCutOff() {
        return cutOff;
    }

    public void setCutOff(Long cutOff) {
        this.cutOff = cutOff;
    }

    public List<SingleDetailReview> getDetailReviews() {
        return detailReviews;
    }

    public void setDetailReviews(List<SingleDetailReview> detailReviews) {
        this.detailReviews = detailReviews;
    }

    class SingleDetailReview {
        public Long id;
        public Float score;
        public String note;
        public Long category;
        public String name;
        public Float weight;
        public String description;

        public SingleDetailReview(DetailReview detailReview) {
            this.id = detailReview.getId();
            this.score = detailReview.getScore();
            this.note = detailReview.getNote();
            this.category = detailReview.getCategory().getId();
            this.name = detailReview.getCategory().getName();
            this.weight = detailReview.getCategory().getWeight();
            this.description = detailReview.getCategory().getDescription();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Float getScore() {
            return score;
        }

        public void setScore(Float score) {
            this.score = score;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Long getCategory() {
            return category;
        }

        public void setCategory(Long category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float getWeight() {
            return weight;
        }

        public void setWeight(Float weight) {
            this.weight = weight;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
