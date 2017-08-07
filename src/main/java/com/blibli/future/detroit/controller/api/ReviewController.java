package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Exception.NotAuthorizedException;
import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.dto.ReviewHistoryDto;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.*;
import com.blibli.future.detroit.service.*;
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
    public static final String DELETE_REVIEW = BASE_PATH + "/{reviewId}";
    public static final String GET_REVIEW_OVERVIEW = BASE_PATH + "/overviews";
    public static final String END_REVIEW_PERIOD = BASE_PATH + "/end-period";
    public static final String GET_REVIEW_HISTORY = BASE_PATH + "/history";

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    ParameterService parameterService;

    @Autowired
    ScoreSummaryService scoreSummaryService;

    @Autowired
    AuthenticationService authenticationService;

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
        try {
            reviewService.createReview(authenticationService.getCurrentUser(), request);
        } catch (NotAuthorizedException e) {
            return new BaseRestResponse(false, e.getClass().getSimpleName(), e.getMessage());
        }
        return new BaseRestResponse();
    }

    @PatchMapping(UPDATE_REVIEW)
    public BaseRestResponse updateReview(@RequestBody NewReviewRequest request) {
        try {
            reviewService.updateReview(authenticationService.getCurrentUser(), request);
        } catch (NotAuthorizedException e) {
            return new BaseRestResponse(false, e.getClass().getSimpleName(), e.getMessage());
        }
        return new BaseRestResponse();
    }

    @DeleteMapping(DELETE_REVIEW)
    public BaseRestResponse<Boolean> deleteReview(@PathVariable Long reviewId) {
        try {
            return new BaseRestResponse<>(reviewService.deleteReview(authenticationService.getCurrentUser(), reviewId));
        } catch (NotAuthorizedException e) {
            return new BaseRestResponse(false, e.getClass().getSimpleName(), e.getMessage());
        }
    }

    @GetMapping(GET_REVIEW_OVERVIEW)
    public BaseRestListResponse<AgentOverviewResponse> getReviewOverview() {
        User currentUser = authenticationService.getCurrentUser();
        return new BaseRestListResponse<>(reviewService.getReviewOverview(currentUser));
    }

    @GetMapping(END_REVIEW_PERIOD)
    public BaseRestResponse<Boolean> endReviewPeriod() {
        return new BaseRestResponse<>(scoreSummaryService.closeCurrentCutOff());
    }

    @GetMapping(GET_REVIEW_HISTORY)
    public BaseRestListResponse<ReviewHistoryDto> getReviewHistory() {
        User currentUser = authenticationService.getCurrentUser();
        return new BaseRestListResponse<>(reviewService.getReviewHistory(currentUser));
    }
}
