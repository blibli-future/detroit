package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.Review;

public class DetailReviewResponse {
    private Long id;
    private String casemgnt;
    private String interactionType;
    private String customerName;
    private String tlName;
    private Float score;
    private Long parameter;
    private Long agent;
    private Long reviewer;

    public DetailReviewResponse(Review review) {
        this.id = review.getId();
        this.casemgnt = review.getCasemgnt();
        this.interactionType = review.getInteractionType();
        this.customerName = review.getCustomerName();
        this.tlName = review.getTlName();
        this.score = review.getScore();
        this.parameter = review.getParameter().getId();
        this.agent = review.getAgent().getId();
        this.reviewer = review.getReviewer().getId();
    }
}
