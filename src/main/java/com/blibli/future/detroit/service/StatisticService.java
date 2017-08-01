package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.enums.ScoreType;
import com.blibli.future.detroit.model.enums.UserType;
import com.blibli.future.detroit.model.response.AgentReviewNoteResponse;
import com.blibli.future.detroit.model.response.StatisticDiagramIndividualResponse;
import com.blibli.future.detroit.model.response.StatisticDiagramResponseNew;
import com.blibli.future.detroit.model.response.StatisticInfoResponse;
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
    @Autowired
    AgentChannelRepository agentChannelRepository;
    @Autowired
    AgentPositionRepository agentPositionRepository;

    public StatisticDiagramResponseNew getCurrentAllStatisticDiagram() {
        List<ParameterStatistic> parameterStatistics = new ArrayList<>();
        List<CategoryStatistic> categoryStatistics =  new ArrayList<>();

        List<CutOffHistory> cutOffHistories = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();
        List<Float> parameterScore = new ArrayList<>();
        List<Float> categoryScore = new ArrayList<>();
        List<Float> totalScore = new ArrayList<>();

        LocalDate now = new LocalDate();

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndInISOFormat() != null) {
                if(cutOffHistory.getEndCutOff().getYear() == now.getYear()) {
                    cutOffHistories.add(cutOffHistory);
                    dates.add(cutOffHistory.getEndCutOff());
                }
            }
        }

        for(CutOffHistory cutOffHistory : cutOffHistories) {
            for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(cutOffHistory)) {
                if (scoreSummary.getScoreType() == ScoreType.TOTAL_REVIEW) {
                    totalScore.add(scoreSummary.getScore());
                }
            }
        }

        for(Parameter parameter : parameterRepository.findAll()) {
            parameterScore = new ArrayList<>();
            categoryStatistics = new ArrayList<>();
            for(Category category : parameter.getCategories()) {
                parameterScore = new ArrayList<>();
                for(CutOffHistory cutOffHistory : cutOffHistories) {
                    for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(cutOffHistory)) {
                        if (parameter.getName().equalsIgnoreCase(scoreSummary.getName())
                            && parameter.getId().equals(scoreSummary.getFkId())
                            && scoreSummary.getAgent() == null) {
                            parameterScore.add(scoreSummary.getScore());
                        }
                        if (category.getName().equalsIgnoreCase(scoreSummary.getName())
                            && category.getId().equals(scoreSummary.getFkId())
                            && scoreSummary.getAgent() == null) {
                            categoryScore.add(scoreSummary.getScore());
                        }
                    }
                }
                categoryStatistics.add(new CategoryStatistic(category.getName(), categoryScore));
                categoryScore = new ArrayList<>();
            }
            parameterStatistics.add(new ParameterStatistic(parameter.getName(), parameterScore, categoryStatistics));
        }

        return new StatisticDiagramResponseNew(dates, totalScore, parameterStatistics);
    }

    public StatisticInfoResponse getCurrentStatisticInfo() {

        Integer totalAgent = userRepository.countAgent();
        CutOffHistory lastCutOff = cutOffRepository.findByEndCutOff(
            cutOffRepository.findByEndCutOffIsNull().getBeginCutOff()
        );
        CutOffHistory beforeLastCutOff = cutOffRepository.findByEndCutOff(lastCutOff.getBeginCutOff());

        Float totalScore = 0f;
        Float totalScoreDiff = 0f;
        Float parameterDiff = 0f;
        List<ParameterStatisticInfo> parameterStatisticInfos = new ArrayList<>();

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(lastCutOff)) {
            if(scoreSummary.getScoreType() == ScoreType.TOTAL_REVIEW) {
                totalScore = scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType() == ScoreType.ALL_PARAMETER) {
                parameterStatisticInfos.add(new ParameterStatisticInfo(scoreSummary.getName(), scoreSummary.getScore()));
            }
        }

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(beforeLastCutOff)) {
            if(scoreSummary.getScoreType() == ScoreType.TOTAL_REVIEW) {
                totalScoreDiff = totalScore - scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType() == ScoreType.ALL_PARAMETER) {
                for(ParameterStatisticInfo parameterStatisticInfo : parameterStatisticInfos) {
                    if(parameterStatisticInfo.getName().equalsIgnoreCase(scoreSummary.getName())) {
                        parameterDiff = parameterStatisticInfo.getScore() - scoreSummary.getScore();
                        parameterStatisticInfo.setDiffScore(parameterDiff);
                    }
                }
            }
        }

        return new StatisticInfoResponse(totalAgent, totalScore, totalScoreDiff, parameterStatisticInfos);
    }

    public List<ScoreSummary> getTopAgent() {
        AgentChannel agentChannel = agentChannelRepository.findOne(6l);
        AgentPosition agentPosition = agentPositionRepository.findOne(8l);
        List<ScoreSummary> scoreSummaries = scoreSummaryRepository.topAgent(agentChannel, agentPosition);

        return scoreSummaries;
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
