package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.Exception.NotAuthorizedException;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.AgentOverviewResponse;
import com.blibli.future.detroit.model.response.OneReviewResponse;
import com.blibli.future.detroit.model.response.UserReviewResponse;
import com.blibli.future.detroit.repository.DetailReviewRepository;
import com.blibli.future.detroit.repository.ParameterRepository;
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
    ParameterRepository parameterRepository;
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

    public Review createReview(User user, NewReviewRequest request) throws NotAuthorizedException {
        checkReviewerIsAuthorized(user, request.getParameter());

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

    public Review updateReview(User user, NewReviewRequest request) throws NotAuthorizedException {
        checkReviewerIsAuthorized(user, request.getParameter());

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
        AgentOverviewResponse agentOverviewResponse = new AgentOverviewResponse();
        List<String> roleList = new ArrayList<>();

        for (UserRole userRole : currentUser.getUserRole()) {
            String rolePrefix = userRole.getRole().substring(0,5);
            if(rolePrefix.equalsIgnoreCase("PARAM")) {
                String role = userRole.getRole().substring(6);
                roleList.add(role);
            }
        }

        Integer reviewcount = 0;

        for(UserRole userRole : currentUser.getUserRole()) {
            String role = userRole.getRole().substring(6);
            for(Parameter parameter : parameterService.getAllParameter()) {
                if (role.equalsIgnoreCase(parameter.getName())) {
                    for(User agent : parameter.getAgentChannel().getUsers()) {
                        agentList.add(agent);
                    }
                }
            }
        }

        if(roleList.size() >0) {
            for(String roles : roleList) {
                for (User agent: agentList) {
                    for (Parameter parameter :  agent.getAgentChannel().getParameters() /*parameterService.getAllParameter()*/) {
                        if (roles.equalsIgnoreCase(parameter.getName())) {
                            List<Review> reviewList = reviewRepository.findByAgentAndParameter(agent, parameter);
                            reviewcount = reviewList.size();
                            Long idAgent = agent.getId();
                            String agentName = agent.getNickname();
                            String agentEmail = agent.getEmail();
                            String agentPosition = agent.getAgentPosition().getName();
                            String agentChannel = agent.getAgentChannel().getName();
                            agentOverviewResponse.addAgents(idAgent, parameter.getId(), agentName, agentEmail, agentPosition, agentChannel, reviewcount);
                        }
                    }
                }
                agentOverviewResponse.setRole(roles);
                agentOverviewResponses.add(agentOverviewResponse);
                agentOverviewResponse = new AgentOverviewResponse();
            }
        }

        return new ArrayList<>(agentOverviewResponses);
    }

    /**
     * Check if reviewer has privilege to review about a parameter.
     *
     * @param reviewer the user who doing the request.
     * @param parameter parameter who want to be updated.
     * @return authorized?
     */
    private boolean checkReviewerIsAuthorized(
            User reviewer, Parameter parameter) throws NotAuthorizedException {
        // Super admin could do anything
        if (reviewer.isSuperAdmin()) {
            return true;
        }
        for (UserRole role: reviewer.getUserRole()) {
            if(role.getRole().equals(parameter.getName())) {
                return true;
            }
        }
        throw new NotAuthorizedException(
            String.format("User \"%s\" doesn't have authorization for parameter \"%s\"",
                          reviewer.getEmail(), parameter.getName()));
    }

    // TODO implement export all review into excel format
}
