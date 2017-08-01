package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.dto.AgentPositionDto;
import com.blibli.future.detroit.model.enums.ScoreType;
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
    AgentPositionRepository agentPositionRepository;
    @Autowired
    AgentChannelRepository agentChannelRepository;

    public StatisticDiagramResponseNew getCurrentAllStatisticDiagram() {
        List<AgentPositionDto> positionDtos = new ArrayList<>();
        List<ParameterStatistic> parameterStatistics = new ArrayList<>();

        List<CutOffHistory> cutOffHistories = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();
        List<Float> parameterScore = new ArrayList<>();
        List<Float> totalScore = new ArrayList<>();

        String positionChannel = "";

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

        for(AgentPosition agentPosition : agentPositionRepository.findAll()) {
            System.out.println(agentPosition.getName());
            for(AgentChannel agentChannel : agentPosition.getAgentChannels()) {
                System.out.println(agentChannel.getName());
                for(Parameter parameter : agentChannel.getParameters()) {
                    for(CutOffHistory cutOffHistory : cutOffHistories) {
                        for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(cutOffHistory)) {
                            if (parameter.getName().equalsIgnoreCase(scoreSummary.getName()) && parameter.getId().equals(scoreSummary.getFkId()) && scoreSummary.getAgent() == null) {
                                System.out.println("parameter ID : "+parameter.getId()+" - score FK : "+scoreSummary.getFkId());
                                parameterScore.add(scoreSummary.getScore());
                            }
                        }
                    }
                    parameterStatistics.add(new ParameterStatistic(parameter.getName(), parameterScore));
                    parameterScore = new ArrayList<>();
                }
                positionDtos.add(new AgentPositionDto(
                    agentPosition.getName()+"-"+agentChannel.getName(),
                    parameterStatistics));
                parameterStatistics = new ArrayList<>();
            }
        }

        return new StatisticDiagramResponseNew(dates, totalScore, positionDtos);
    }

    public StatisticInfoResponse getCurrentStatisticInfo() {

        Integer totalAgent = userRepository.countAgent();
        CutOffHistory lastCutOff = cutOffRepository.findByEndCutOff(
            cutOffRepository.findByEndCutOffIsNull().getBeginCutOff()
        );
        CutOffHistory beforeLastCutOff = cutOffRepository.findByEndCutOff(lastCutOff.getBeginCutOff());

        Float totalScore = 0f;
        Float totalScoreDiff = 0f;
        Float channelDiff = 0f;
        List<ChannelStatisticInfo> channelStatisticInfos = new ArrayList<>();

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(lastCutOff)) {
            if(scoreSummary.getScoreType().equals(ScoreType.TOTAL_REVIEW)) {
                totalScore = scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType().equals(ScoreType.TOTAL_CHANNEL)) {
                AgentChannel agentChannel = agentChannelRepository.findOne(scoreSummary.getFkId());
                channelStatisticInfos.add(
                    new ChannelStatisticInfo(agentChannel.getAgentPosition().getName()+"-"+agentChannel.getName()
                        , scoreSummary.getScore()));
            }
        }

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(beforeLastCutOff)) {
            if(scoreSummary.getScoreType().equals(ScoreType.TOTAL_REVIEW)) {
                totalScoreDiff = totalScore - scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType().equals(ScoreType.TOTAL_CHANNEL)) {
                for(ChannelStatisticInfo channelStatisticInfo : channelStatisticInfos) {
                    AgentChannel agentChannel = agentChannelRepository.findOne(scoreSummary.getFkId());
                    String channelName = agentChannel.getAgentPosition().getName()+"-"+agentChannel.getName();
                    if(channelStatisticInfo.getName().equalsIgnoreCase(channelName)) {
                        channelDiff = channelStatisticInfo.getScore() - scoreSummary.getScore();
                        channelStatisticInfo.setDiffScore(channelDiff);
                    }
                }
            }
        }

        return new StatisticInfoResponse(totalAgent, totalScore, totalScoreDiff, channelStatisticInfos);
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
