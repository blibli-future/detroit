package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.response.AgentReviewNoteResponse;
import com.blibli.future.detroit.model.response.StatisticDiagramIndividualResponse;
import com.blibli.future.detroit.model.response.StatisticDiagramResponseNew;
import com.blibli.future.detroit.repository.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    DetailReviewRepository detailReviewRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ScoreSummaryRepository scoreSummaryRepository;
    @Autowired
    CutOffRepository cutOffRepository;

    public StatisticDiagramResponseNew getCurrentAllStatisticDiagram() {
        List<ParameterStatistic> parameterStatistics = new ArrayList<>();
        List<CategoryStatistic> categoryStatistics =  new ArrayList<>();

        List<CutOffHistory> cutOffHistories = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();
        List<Float> parameterScore = new ArrayList<>();
        List<Float> categoryScore = new ArrayList<>();

        LocalDate now = new LocalDate();

        System.out.println(now.getYear());

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndInISOFormat() != null) {
                if(cutOffHistory.getEndCutOff().getYear() == now.getYear()) {
                    cutOffHistories.add(cutOffHistory);
                    dates.add(cutOffHistory.getEndCutOff());
                }
            }
        }
        for(Parameter parameter : parameterRepository.findAll()) {
            for(Category category : parameter.getCategories()) {
                for(CutOffHistory cutOffHistory : cutOffHistories) {
                    for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(cutOffHistory)) {
                        if (category.getName() == scoreSummary.getName() && category.getId() == scoreSummary.getFkId() && scoreSummary.getAgent() == null) {
                            categoryScore.add(scoreSummary.getScore());
                        }
                    }
                }
                categoryStatistics.add(new CategoryStatistic(category.getName(), categoryScore));
                categoryScore = new ArrayList<>();
            }
        }

        for(Parameter parameter : parameterRepository.findAll()) {
                for(CutOffHistory cutOffHistory : cutOffHistories) {
                    for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(cutOffHistory)) {
                        if (parameter.getName() == scoreSummary.getName() && parameter.getId() == scoreSummary.getFkId() && scoreSummary.getAgent() == null) {
                            parameterScore.add(scoreSummary.getScore());
                        }
                    }
                }
            parameterStatistics.add(new ParameterStatistic(parameter.getName(), parameterScore, categoryStatistics));
            parameterScore = new ArrayList<>();
        }

        return new StatisticDiagramResponseNew(dates, parameterStatistics);
    }

    public StatisticInfo getCurrentStatisticInfo() {
        StatisticInfo statisticInfo = new StatisticInfo();
        statisticInfo.setTotalAgent(userRepository.countAgent());

        HashMap<String, Float> parameterAvgMap = new HashMap<>();
        HashMap<String, Integer> parameterCountMap = new HashMap<>();;
        LocalDate now = new LocalDate();

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndInISOFormat() != null) {
                continue;
            } else {
                if(cutOffHistory.getEndCutOff().getYear() != now.getYear()) {
                    continue;
                }
                for(Review review : cutOffHistory.getReviews()) {
                    String parameter = review.getParameter().getName();
                    Integer currentCountParameter = parameterCountMap.getOrDefault(parameter, 0);
                    parameterCountMap.put(parameter, currentCountParameter+1);

                    Float currentParameter = review.getScore();

                    Float currentAvgParameter = parameterAvgMap.getOrDefault(parameter, 0f);
                    Float nextAvgParameter = ((currentAvgParameter*currentCountParameter)+currentParameter) / (currentCountParameter+1);
                    parameterAvgMap.put(parameter, nextAvgParameter);
                }
                for (Map.Entry<String, Float> entry : parameterAvgMap.entrySet()) {
                    String key = entry.getKey();
                    Float value = entry.getValue();

                    statisticInfo.addParameterScores(key, value);
                }
            }
        }

        CutOffHistory cutOffHistory = cutOffRepository.findByEndCutOffIsNull();
        Long cutOffId = cutOffHistory.getId();

        for(Parameter parameter : parameterRepository.findAll()) {
            for (User user : userRepository.findAll()) {
                Long reviewerId = user.getId();
                Long parameterId = parameter.getId();
                Integer countValue = reviewRepository.countByReviewer(user, cutOffHistory, parameter);
                statisticInfo.addReviewCounts(parameter.getName(), user.getFullname(), countValue);
            }
        }
        return statisticInfo;
    }

    public StatisticInfoIndividual getIndividualStatisticInfo(Long agentId) {
        StatisticInfoIndividual statisticInfoIndividual = new StatisticInfoIndividual();
        Float finalScore = 0f;
        HashMap<String, Float> parameterScore = new HashMap<>();
        HashMap<String, Integer> parameterCount = new HashMap<>();

        User user = userRepository.findOne(agentId);
        CutOffHistory cutOffLast = cutOffRepository.findByEndCutOffIsNull();
        CutOffHistory cutOff = cutOffRepository.findByEndCutOff(cutOffLast.getBeginCutOff());

        List<Review> reviewList = reviewRepository.findByAgentAndCutOffHistory(user, cutOff);

        for (Review review : reviewList) {
            String parameterName = review.getParameter().getName();

            finalScore = finalScore + (review.getScore()*(review.getParameter().getWeight()/100) );

            Integer currentCount = parameterCount.getOrDefault(parameterName, 0);
            parameterCount.put(parameterName, currentCount+1);

            Float currentAvg = parameterScore.getOrDefault(parameterName, 0f);
            Float nextAvg = ((currentAvg*currentCount)+review.getScore()) / (currentCount+1);
            parameterScore.put(parameterName,nextAvg);
        }
        statisticInfoIndividual.setTotalScore(finalScore);
        statisticInfoIndividual.setParameterScore(parameterScore);

        return statisticInfoIndividual;
    }

    public List<StatisticDiagramIndividualResponse> getIndividualStatisticDiagram(Long agentId) {
        List<StatisticDiagramIndividualResponse> statisticDiagramIndividualResponses = new ArrayList<>();
        User agent = userRepository.findOne(agentId);
        Float finalScore = 0f;
        LocalDate now = new LocalDate();

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndInISOFormat() == null) {
                continue;
            } else {
                if(cutOffHistory.getEndCutOff().getYear() != now.getYear()) {
                    continue;
                }
                for(Review review : cutOffHistory.getReviews()) {
                    if(review.getAgent() != agent) {
                        continue;
                    }
                    finalScore = finalScore + (review.getScore()*(review.getParameter().getWeight()/100) );
                }
                statisticDiagramIndividualResponses.add(new StatisticDiagramIndividualResponse(cutOffHistory.getEndCutOff(), finalScore));
            }
        }
        return statisticDiagramIndividualResponses;
    }

    public List<AgentReviewNoteResponse> getIndividualReviewNote(Long agentId) {
        List<AgentReviewNoteResponse> agentReviewNoteResponses = new ArrayList<>();

        User agent = userRepository.findOne(agentId);
        CutOffHistory cutOffLast = cutOffRepository.findByEndCutOffIsNull();
        CutOffHistory cutOff = cutOffRepository.findByEndCutOff(cutOffLast.getBeginCutOff());

        List<Review> reviewList = reviewRepository.findByAgentAndCutOffHistory(agent, cutOff);

        for (Review review : reviewList) {
            for (DetailReview detailReview : detailReviewRepository.findByReview(review)) {
                if(detailReview.getNote() == null) {
                    continue;
                }
                String categoryName = detailReview.getCategory().getName();
                Float detailReviewScore = detailReview.getScore();
                String note = detailReview.getNote();
                agentReviewNoteResponses.add(new AgentReviewNoteResponse(categoryName, detailReviewScore, note));
            }
        }
        return agentReviewNoteResponses;
    }
}
