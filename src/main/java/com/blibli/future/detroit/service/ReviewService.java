package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.AgentOverviewResponse;
import com.blibli.future.detroit.model.response.OneReviewResponse;
import com.blibli.future.detroit.model.response.UserReviewResponse;
import com.blibli.future.detroit.repository.DetailReviewRepository;
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
    DetailReviewRepository detailReviewRepository;
    @Autowired
    UserService userService;
    @Autowired
    ParameterService parameterService;
    @Autowired
    Converter modelMapper;

    public List<UserReviewResponse> getAllReview(Long userId) {
        // TODO different fetching logic based on
        //      UserType.
        List<UserReviewResponse> userReviewResponses = new ArrayList<>();
        User user = userService.getOneUser(userId);
        if (user.isAgent()) {
            List<Review> reviews = reviewRepository.findByAgentId(userId);
            for (Review review : reviews) {
                userReviewResponses.add(new UserReviewResponse(review));
            }
            return  new ArrayList<>(userReviewResponses);
        }
        return new ArrayList<>();
    }

    public OneReviewResponse getOneReview(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        return new OneReviewResponse(review);
    }

    public Review createReview(NewReviewRequest request) {
        Float score = 0f;

        for (DetailReview detailReview : request.getDetailReviews()) {
            score = score + (detailReview.getCategory().getWeight() / 100) * detailReview.getScore();
        }

        Review newReview = modelMapper.modelMapper()
            .map(request, Review.class);
        newReview.setScore(score);
        newReview = reviewRepository.saveAndFlush(newReview);

        detailReviewRepository.save(request.getDetailReviews());
        return newReview;
    }

    public Review updateReview(NewReviewRequest request) {
        Review updatedReview = modelMapper.modelMapper()
            .map(request, Review.class);
        if (!reviewRepository.exists(updatedReview.getId())) {
            throw new EntityNotFoundException();
        }
        reviewRepository.save(updatedReview);
        detailReviewRepository.save(request.getDetailReviews());
        return updatedReview;
    }

    public List<AgentOverviewResponse> getReviewOverview(User currentUser) {
        List<User> agentList = new ArrayList<>();
        List<AgentOverviewResponse> agentOverviewResponses = new ArrayList<>();

        Integer reviewcount = 0;

        for(UserRole userRole : currentUser.getUserRole()) {
            String role = userRole.getRole().substring(6);
            System.out.println(role);
            for(Parameter parameter : parameterService.getAllParameter()) {
                if (role.equalsIgnoreCase(parameter.getName())) {
                    System.out.println(parameter.getName());
                    for(User agent : parameter.getAgentChannel().getUsers()) {
                        System.out.println(agent.getFullname());
                        agentList.add(agent);
                    }
                }
            }
        }


        for (User agent: agentList) {
            HashMap<String, Integer> map = new HashMap<>();
            for (UserRole userRole : currentUser.getUserRole()) {
                String role = userRole.getRole().substring(6);
                for (Parameter parameter : parameterService.getAllParameter()) {
                    if (role.equalsIgnoreCase(parameter.getName())) {
                        List<Review> reviewList = reviewRepository.findByAgentAndParameter(agent, parameter);
                        reviewcount = reviewList.size();
                    }
                }
            }
            Long idAgent = agent.getId();
            String agentName = agent.getNickname();
            String agentEmail = agent.getEmail();
            String agentPosition = agent.getAgentPosition().getName();
            String agentChannel = agent.getAgentChannel().getName();
            agentOverviewResponses.add(new AgentOverviewResponse(idAgent, agentName, agentEmail, agentPosition, agentChannel, reviewcount));
        }


//        for (User agent: agentList) {
//            HashMap<String, Integer> map = new HashMap<>();
//            for (Parameter parameter : parameterList) {
//                List<Review> reviewList = reviewRepository.findByAgentAndParameter(agent, parameter);
//                map.put(parameter.getName(), reviewList.size());
//            }
//            agentOverviewResponses.add(new AgentOverviewResponse(agent, map));
//        }
        return new ArrayList<>(agentOverviewResponses);
    }

    // TODO implement export all review into excel format
}
