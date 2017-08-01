package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.*;
import com.blibli.future.detroit.service.ParameterService;
import com.blibli.future.detroit.service.ReviewService;
import com.blibli.future.detroit.service.ScoreSummaryService;
import com.blibli.future.detroit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    public static final String BASE_PATH = "/api/v1/reviews";
    public static final String GET_ALL_REVIEW = BASE_PATH + "/user/{userId}"; // TODO get userId from auth system
    public static final String GET_ONE_REVIEW = BASE_PATH + "/{reviewId}";
    public static final String CREATE_REVIEW  = BASE_PATH;
    public static final String UPDATE_REVIEW  = BASE_PATH + "/{reviewId}";
    public static final String GET_REVIEW_OVERVIEW = BASE_PATH + "/overviews";
    public static final String END_REVIEW_PERIOD = BASE_PATH + "/end-period";

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    ParameterService parameterService;

    @Autowired
    ScoreSummaryService scoreSummaryService;

    @GetMapping(GET_ALL_REVIEW)
    public BaseRestListResponse<UserReviewResponse> getAllReview(@PathVariable Long userId) {
        return new BaseRestListResponse<>(reviewService.getAllReview(userId));
    }

    @GetMapping(GET_ONE_REVIEW)
    public BaseRestResponse<OneReviewResponse> getOneReview(@PathVariable Long reviewId) {
        OneReviewResponse oneReviewResponse = reviewService.getOneReview(reviewId);
        return new BaseRestResponse<>(oneReviewResponse);
    }

    @PostMapping(CREATE_REVIEW)
    public BaseRestResponse createReview(@RequestBody NewReviewRequest request) {
        reviewService.createReview(request);
        return new BaseRestResponse();
    }

    @PatchMapping(UPDATE_REVIEW)
    public BaseRestResponse updateReview(@RequestBody NewReviewRequest request) {
        reviewService.updateReview(request);
        return new BaseRestResponse();
    }

    @GetMapping(GET_REVIEW_OVERVIEW)
    public BaseRestListResponse<AgentOverviewResponse> getReviewOverview() {
        return new BaseRestListResponse<>(reviewService.getReviewOverview());
    }

    @GetMapping(END_REVIEW_PERIOD)
    public BaseRestResponse<Boolean> endReviewPeriod() {
        return new BaseRestResponse<>(scoreSummaryService.closeCurrentCutOff());
    }
}
