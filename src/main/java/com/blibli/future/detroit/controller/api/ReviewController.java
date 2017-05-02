package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.AgentOverviewResponse;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.ParameterService;
import com.blibli.future.detroit.service.ReviewService;
import com.blibli.future.detroit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    public static final String BASE_PATH = "/api/v1/reviews";
    public static final String GET_ALL_REVIEW = BASE_PATH + "/{userId}"; // TODO get userId from auth system
    public static final String GET_ONE_REVIEW = BASE_PATH + "/{reviewId}";
    public static final String CREATE_REVIEW  = BASE_PATH;
    public static final String UPDATE_REVIEW  = BASE_PATH + "/{reviewId}";
    public static final String GET_REVIEW_OVERVIEW = BASE_PATH + "/overviews";

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    ParameterService parameterService;

    @GetMapping(GET_ALL_REVIEW)
    public BaseRestListResponse<Review> getAllReview(@PathVariable Long userId) {
        List<Review> reviewList = reviewService.getAllReview(userId);
        return new BaseRestListResponse<>(reviewList);
    }

    @GetMapping(GET_ONE_REVIEW)
    public BaseRestResponse<Review> getOneReview(@PathVariable Long reviewId) {
        Review review = reviewService.getOneReview(reviewId);
        return new BaseRestResponse<>(review);
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
}
