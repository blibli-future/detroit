package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.enums.ScoreType;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.repository.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreSummaryService {
    @Autowired
    ScoreSummaryRepository scoreSummaryRepository;
    @Autowired
    AgentChannelRepository agentChannelRepository;
    @Autowired
    CutOffRepository cutOffRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    DetailReviewRepository detailReviewRepository;

    public boolean closeCurrentCutOff() {
        CutOffHistory currentCutOff = cutOffRepository.findByEndCutOffIsNull();

        Float totalParameterUser = 0f;
        Float totalCategoryUser = 0f;
        Integer countParameterUser = 0;
        Integer countCategoryUser = 0;

        for (User user : userRepository.findByUserType(UserType.AGENT)) {
            for(Parameter parameter : parameterRepository.findByAgentChannel(user.getAgentChannel())) {
                for (Review review : reviewRepository.findByAgentAndCutOffHistoryAndParameter(user, currentCutOff,parameter)) {
                    for(Category category : parameter.getCategories()) {
                        for(DetailReview detailReview : detailReviewRepository.findByReviewAndCategory(review, category)) {
                            totalCategoryUser = totalCategoryUser + detailReview.getScore();
                            countCategoryUser++;
                        }
                        if(countCategoryUser == 0) {
                            continue;
                        }
                        totalCategoryUser = totalCategoryUser / countCategoryUser;
                        scoreSummaryRepository.save(
                            new ScoreSummary(category.getName(),
                                totalCategoryUser,
                                ScoreType.USER_CATEGORY,
                                category.getId(),
                                currentCutOff,
                                user)
                        );
                        totalCategoryUser = 0f;
                        countCategoryUser = 0;
                    }
                    totalParameterUser = totalParameterUser + review.getScore();
                    countParameterUser++;
                }
                if(countParameterUser == 0 {
                    continue;
                }
                totalParameterUser = totalParameterUser / countParameterUser;
                System.out.println();
                scoreSummaryRepository.save(
                    new ScoreSummary(parameter.getName(),
                        totalParameterUser,
                        ScoreType.USER_PARAMETER,
                        parameter.getId(),
                        currentCutOff,
                        user)
                );
                totalParameterUser = 0f;
                countParameterUser = 0;
            }
        }

        Float finalScoreUser = 0f;
        String totalUser = "User Total";

        for (User user : userRepository.findByUserType(UserType.AGENT)) {
            for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(currentCutOff, user)) {
                if(scoreSummary.getScoreType().equals(ScoreType.USER_PARAMETER)) {
                    Parameter parameter = parameterRepository.findOne(scoreSummary.getFkId());
                    finalScoreUser = finalScoreUser + (scoreSummary.getScore()*parameter.getWeight()/100);
                }
            }
            if(finalScoreUser == 0f) {
                continue;
            }
            scoreSummaryRepository.save(
                new ScoreSummary(totalUser,
                    finalScoreUser,
                    ScoreType.USER_TOTAL,
                    null,
                    currentCutOff,
                    user)
            );
            finalScoreUser = 0f;
        }

        Float finalParameter = 0f;
        Integer countParameter = 0;

        for(Parameter parameter : parameterRepository.findAll()) {
            for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndFkId(currentCutOff, parameter.getId())) {
                finalParameter = finalParameter + scoreSummary.getScore();
                countParameter++;
            }
            if(countParameter == 0) {
                continue;
            }
            finalParameter = finalParameter / countParameter;
            scoreSummaryRepository.save(
                new ScoreSummary(parameter.getName(),
                    finalParameter,
                    ScoreType.ALL_PARAMETER,
                    parameter.getId(),
                    currentCutOff,
                    null)
            );
            finalParameter = 0f;
            countParameter = 0;
        }

        Float finalCategory = 0f;
        Integer countCategory = 0;

        for(Category category : categoryRepository.findAll()) {
            for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndFkId(currentCutOff, category.getId())) {
                finalCategory = finalCategory + scoreSummary.getScore();
                countCategory++;
            }
            if(countCategory == 0) {
                continue;
            }
            finalCategory = finalCategory / countCategory;
            scoreSummaryRepository.save(
                new ScoreSummary(category.getName(),
                    finalCategory,
                    ScoreType.ALL_CATEGORY,
                    category.getId(),
                    currentCutOff,
                    null)
            );
            finalCategory = 0f;
            countCategory = 0;
        }

        Float finalChannel = 0f;
        Integer countChannel = 0;

        for(AgentChannel channel : agentChannelRepository.findAll()) {
            for (User user : channel.getUsers()) {
                for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgentAndScoreType(currentCutOff, user, ScoreType.USER_TOTAL)) {
                    finalChannel = finalChannel + scoreSummary.getScore();
                    countChannel++;
                }
            }
            if(channel.getUsers().size() >0) {
                finalChannel = finalChannel / countChannel;
                scoreSummaryRepository.save(
                    new ScoreSummary(channel.getName(),
                        finalChannel,
                        ScoreType.TOTAL_CHANNEL,
                        channel.getId(),
                        currentCutOff,
                        null)
                );
                finalChannel = 0f;
                countChannel = 0;
            }
        }

        currentCutOff.setEndCutOff(new LocalDate());
        currentCutOff = cutOffRepository.saveAndFlush(currentCutOff);

        CutOffHistory newCutOff = new CutOffHistory();
        newCutOff.setBeginCutOff(currentCutOff.getEndCutOff());
        cutOffRepository.save(newCutOff);

        return true;
    }
}
