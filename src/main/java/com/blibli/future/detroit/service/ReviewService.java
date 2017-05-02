package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.AgentOverviewResponse;
import com.blibli.future.detroit.repository.ReviewRepository;
import com.blibli.future.detroit.util.configuration.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    Converter modelMapper;

    public List<Review> getAllReview(Long userId) {
        // TODO different fetching logic based on
        //      UserType.
        User user = userService.getOneUser(userId);
        if (user.isAgent()) {
            return reviewRepository.findByReviewerId(userId);
        }
        return new ArrayList<>();
    }

    public Review getOneReview(Long reviewId) {
        return reviewRepository.findOne(reviewId);
    }

    public Review createReview(NewReviewRequest request) {
        Review newReview = modelMapper.modelMapper()
            .map(request, Review.class);
        reviewRepository.save(newReview);
        return newReview;
    }

    public Review updateReview(NewReviewRequest request) {
        Review updatedReview = modelMapper.modelMapper()
            .map(request, Review.class);
        if (!reviewRepository.exists(updatedReview.getId())) {
            throw new EntityNotFoundException();
        }
        reviewRepository.save(updatedReview);
        return updatedReview;
    }

    public List<AgentOverviewResponse> getReviewOverview() {
        List<User> agentList = userService.getAllUser(UserType.AGENT);
        List<Parameter> parameterList = categoryService.getAllCategory();
        List<AgentOverviewResponse> agentOverviewResponses = new ArrayList<>();
        for (User agent: agentList) {
            HashMap<String, Integer> map = new HashMap<>();
            for (Parameter parameter : parameterList) {
                List<Review> reviewList = reviewRepository.findByAgentAndParameter(agent, parameter);
                map.put(parameter.getName(), reviewList.size());
            }
            agentOverviewResponses.add(new AgentOverviewResponse(agent, map));
        }
        return new ArrayList<>(agentOverviewResponses);
    }

    // TODO implement export all review into excel format
}
