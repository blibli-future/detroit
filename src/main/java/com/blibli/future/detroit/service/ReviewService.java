package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.Exception.NotAuthorizedException;
import com.blibli.future.detroit.model.dto.DetailReviewDto;
import com.blibli.future.detroit.model.dto.ReviewHistoryDto;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.request.NewReviewRequest;
import com.blibli.future.detroit.model.response.AgentOverviewResponse;
import com.blibli.future.detroit.model.response.OneReviewResponse;
import com.blibli.future.detroit.model.response.UserReviewResponse;
import com.blibli.future.detroit.repository.*;
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
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CutOffRepository cutOffRepository;
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
        Parameter parameter = parameterRepository.findOne(request.getParameter());
        checkReviewerIsAuthorized(user, parameter);

        CutOffHistory currentCutOff = cutOffRepository.findByEndCutOffIsNull();

        Float score = 0f;

        for (DetailReviewDto detailReviewDto : request.getDetailReviews()) {
            Category category = categoryRepository.getOne(detailReviewDto.getCategory());
            score = score + ((category.getWeight() / 100) * detailReviewDto.getScore());
        }

        User agent = userRepository.getOne(request.getAgent());

        Review newReview = new Review();
        newReview.setCasemgnt(request.getCasemgnt());
        newReview.setInteractionType(request.getInteractionType());
        newReview.setCustomerName(request.getCustomerName());
        newReview.setParameter(parameter);
        newReview.setScore(score);
        newReview.setReviewer(user);
        newReview.setAgent(agent);
        newReview.setCutOffHistory(currentCutOff);

        newReview = reviewRepository.saveAndFlush(newReview);

        List<DetailReview> detailReviews = new ArrayList<>();

        for (DetailReviewDto detailReviewDto : request.getDetailReviews()) {
            Category category = categoryRepository.getOne(detailReviewDto.getCategory());
            detailReviews.add(new DetailReview(detailReviewDto.getScore(),
                detailReviewDto.getNote(), newReview, category));
        }

        detailReviewRepository.save(detailReviews);

        return newReview;
    }

    public Review updateReview(User user, NewReviewRequest request) throws NotAuthorizedException {
        Parameter parameter = parameterRepository.findOne(request.getParameter());
        checkReviewerIsAuthorized(user, parameter);

        if (!reviewRepository.exists(request.getId())) {
            throw new EntityNotFoundException();
        }

        Float score = 0f;

        for (DetailReviewDto detailReviewDto : request.getDetailReviews()) {
            Category category = categoryRepository.getOne(detailReviewDto.getCategory());
            score = score + ((category.getWeight() / 100) * detailReviewDto.getScore());
        }

        Review updatedReview = reviewRepository.findOne(request.getId());
        updatedReview.setCasemgnt(request.getCasemgnt());
        updatedReview.setInteractionType(request.getInteractionType());
        updatedReview.setCustomerName(request.getCustomerName());
        updatedReview.setParameter(parameter);
        updatedReview.setScore(score);
        updatedReview = reviewRepository.saveAndFlush(updatedReview);

        for (DetailReviewDto detailReviewDto : request.getDetailReviews()) {
            if (!detailReviewRepository.exists(detailReviewDto.getId())) {
                throw new EntityNotFoundException();
            }
            DetailReview detailReview = detailReviewRepository.findOne(detailReviewDto.getId());
            Category category = categoryRepository.getOne(detailReviewDto.getCategory());
            detailReview.setScore(detailReviewDto.getScore());
            detailReview.setNote(detailReviewDto.getNote());
            detailReview.setCategory(category);
            detailReview.setReview(updatedReview);
            detailReviewRepository.save(detailReview);
        }

        return updatedReview;
    }

    public Boolean deleteReview(User currentUser, Long reviewId) throws NotAuthorizedException {
        Boolean check;

        Review review = reviewRepository.findOne(reviewId);

        checkReviewerIsAuthorized(currentUser, review.getParameter());

        if(review.getReviewer().equals(currentUser)) {
            reviewRepository.delete(reviewId);
            check = true;
        } else {
            check = false;
        }

        return check;
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

    public List<ReviewHistoryDto> getReviewHistory(User currentUser) {
        List<ReviewHistoryDto> reviewHistoryDtos = new ArrayList<>();
        CutOffHistory currentCutOff = cutOffRepository.findByEndCutOffIsNull();


        for(Review review : reviewRepository.findByReviewerAndCutOffHistory(currentUser, currentCutOff)) {
            reviewHistoryDtos.add(new ReviewHistoryDto(
                review.getId(),
                review.getAgent().getEmail(),
                review.getAgent().getFullname(),
                review.getParameter().getAgentChannel().getAgentPosition().getName(),
                review.getParameter().getAgentChannel().getName(),
                review.getParameter().getName(),
                review.getScore(),
                review.getCreatedAtInISOFormat()
            ));
        }

        return reviewHistoryDtos;
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
            String roleParameter = role.getRole().substring(6);
            if(roleParameter.equalsIgnoreCase(parameter.getName())) {
                return true;
            }
        }
        throw new NotAuthorizedException(
            String.format("User \"%s\" doesn't have authorization for parameter \"%s\"",
                          reviewer.getEmail(), parameter.getName()));
    }

    // TODO implement export all review into excel format
}
