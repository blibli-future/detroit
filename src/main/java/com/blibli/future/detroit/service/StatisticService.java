package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.dto.AgentPositionDto;
import com.blibli.future.detroit.model.dto.StatisticDto;
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
            for(AgentChannel agentChannel : agentPosition.getAgentChannels()) {
                for(Parameter parameter : agentChannel.getParameters()) {
                    for(CutOffHistory cutOffHistory : cutOffHistories) {
                        for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistory(cutOffHistory)) {
                            if (parameter.getName().equalsIgnoreCase(scoreSummary.getName()) && parameter.getId().equals(scoreSummary.getFkId()) && scoreSummary.getAgent() == null) {
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

    public StatisticDiagramIndividualResponse getIndividualStatisticDiagram(Long agentId) {
        User agent = userRepository.findOne(agentId);

        List<LocalDate> dates = new ArrayList<>();
        List<Float> totalScore = new ArrayList<>();

        LocalDate now = new LocalDate();
        List<CutOffHistory> cutOffHistories = new ArrayList<>();

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndCutOff() != null) {
                if(cutOffHistory.getEndCutOff().getYear() == now.getYear()) {
                    cutOffHistories.add(cutOffHistory);
                    dates.add(cutOffHistory.getEndCutOff());
                }
            }
        }

        for(CutOffHistory cutOffHistory : cutOffHistories) {
            for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(cutOffHistory, agent)) {
                if (scoreSummary.getScoreType() == ScoreType.USER_TOTAL) {
                    totalScore.add(scoreSummary.getScore());
                }
            }
        }

        List<Float> parameterScore = new ArrayList<>();
        List<Float> categoryScore = new ArrayList<>();

        List<StatisticDto> parameterStatistics = new ArrayList<>();
        List<StatisticDto> categoryStatistics = new ArrayList<>();

        for(Parameter parameter : agent.getAgentChannel().getParameters()) {
            parameterScore = new ArrayList<>();
            categoryStatistics = new ArrayList<>();
            for(Category category : parameter.getCategories()) {
                parameterScore = new ArrayList<>();
                for(CutOffHistory cutOffHistory : cutOffHistories) {
                    for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(cutOffHistory,agent)) {
                        if (parameter.getName().equalsIgnoreCase(scoreSummary.getName()) && parameter.getId().equals(scoreSummary.getFkId()) && scoreSummary.getAgent() != null) {
                            parameterScore.add(scoreSummary.getScore());
                        }
                        if (category.getName().equalsIgnoreCase(scoreSummary.getName()) && category.getId().equals(scoreSummary.getFkId()) && scoreSummary.getAgent() != null) {
                            categoryScore.add(scoreSummary.getScore());
                        }
                    }
                }
                categoryStatistics.add(new StatisticDto(category.getName(), categoryScore));
                categoryScore = new ArrayList<>();
            }
            parameterStatistics.add(new StatisticDto(parameter.getName(), parameterScore, categoryStatistics));
        }

        return new StatisticDiagramIndividualResponse(dates, totalScore, parameterStatistics);
    }

    public StatisticInfoResponse getIndividualStatisticInfo(Long agentId) {

        User agent = userRepository.findOne(agentId);
        CutOffHistory lastCutOff = cutOffRepository.findByEndCutOff(
            cutOffRepository.findByEndCutOffIsNull().getBeginCutOff()
        );
        CutOffHistory beforeLastCutOff = cutOffRepository.findByEndCutOff(lastCutOff.getBeginCutOff());

        Float totalScore = 0f;
        Float totalScoreDiff = 0f;
        Float channelDiff = 0f;
        List<ChannelStatisticInfo> channelStatisticInfos = new ArrayList<>();

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(lastCutOff, agent)) {
            if(scoreSummary.getScoreType().equals(ScoreType.USER_TOTAL)) {
                totalScore = scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType().equals(ScoreType.USER_PARAMETER)) {
                channelStatisticInfos.add(
                    new ChannelStatisticInfo(scoreSummary.getName()
                        , scoreSummary.getScore()));
            }
        }

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(beforeLastCutOff, agent)) {
            if(scoreSummary.getScoreType().equals(ScoreType.USER_TOTAL)) {
                totalScoreDiff = totalScore - scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType().equals(ScoreType.USER_PARAMETER)) {
                for(ChannelStatisticInfo channelStatisticInfo : channelStatisticInfos) {
                    Parameter parameter = parameterRepository.findOne(scoreSummary.getFkId());
                    String parameterName = parameter.getName();
                    if(channelStatisticInfo.getName().equalsIgnoreCase(parameterName)) {
                        channelDiff = channelStatisticInfo.getScore() - scoreSummary.getScore();
                        channelStatisticInfo.setDiffScore(channelDiff);
                    }
                }
            }
        }

        return new StatisticInfoResponse(totalScore, totalScoreDiff, channelStatisticInfos);
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
                String categoryName = review.getParameter().getName()+"-"+detailReview.getCategory().getName();
                Float detailReviewScore = detailReview.getScore();
                String note = detailReview.getNote();
                agentReviewNoteResponses.add(new AgentReviewNoteResponse(categoryName, detailReviewScore, note));
            }
        }
        return agentReviewNoteResponses;
    }

    public StatisticDiagramIndividualResponse getAgentDiagram(User agent) {
        List<LocalDate> dates = new ArrayList<>();
        List<Float> totalScore = new ArrayList<>();

        LocalDate now = new LocalDate();
        List<CutOffHistory> cutOffHistories = new ArrayList<>();

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndCutOff() != null) {
                if(cutOffHistory.getEndCutOff().getYear() == now.getYear()) {
                    cutOffHistories.add(cutOffHistory);
                    dates.add(cutOffHistory.getEndCutOff());
                }
            }
        }

        for(CutOffHistory cutOffHistory : cutOffHistories) {
            for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(cutOffHistory, agent)) {
                if (scoreSummary.getScoreType() == ScoreType.USER_TOTAL) {
                    totalScore.add(scoreSummary.getScore());
                }
            }
        }

        List<Float> parameterScore = new ArrayList<>();
        List<Float> categoryScore = new ArrayList<>();

        List<StatisticDto> parameterStatistics = new ArrayList<>();
        List<StatisticDto> categoryStatistics = new ArrayList<>();

        for(Parameter parameter : agent.getAgentChannel().getParameters()) {
            parameterScore = new ArrayList<>();
            categoryStatistics = new ArrayList<>();
            for(Category category : parameter.getCategories()) {
                parameterScore = new ArrayList<>();
                for(CutOffHistory cutOffHistory : cutOffHistories) {
                    for (ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(cutOffHistory,agent)) {
                        if (parameter.getName().equalsIgnoreCase(scoreSummary.getName()) && parameter.getId().equals(scoreSummary.getFkId()) && scoreSummary.getAgent() != null) {
                            parameterScore.add(scoreSummary.getScore());
                        }
                        if (category.getName().equalsIgnoreCase(scoreSummary.getName()) && category.getId().equals(scoreSummary.getFkId()) && scoreSummary.getAgent() != null) {
                            categoryScore.add(scoreSummary.getScore());
                        }
                    }
                }
                categoryStatistics.add(new StatisticDto(category.getName(), categoryScore));
                categoryScore = new ArrayList<>();
            }
            parameterStatistics.add(new StatisticDto(parameter.getName(), parameterScore, categoryStatistics));
        }

        return new StatisticDiagramIndividualResponse(dates, totalScore, parameterStatistics);
    }

    public StatisticInfoResponse getAgentInfo(User agent) {

        CutOffHistory lastCutOff = cutOffRepository.findByEndCutOff(
            cutOffRepository.findByEndCutOffIsNull().getBeginCutOff()
        );
        CutOffHistory beforeLastCutOff = cutOffRepository.findByEndCutOff(lastCutOff.getBeginCutOff());

        Float totalScore = 0f;
        Float totalScoreDiff = 0f;
        Float channelDiff = 0f;
        List<ChannelStatisticInfo> channelStatisticInfos = new ArrayList<>();

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(lastCutOff, agent)) {
            if(scoreSummary.getScoreType().equals(ScoreType.USER_TOTAL)) {
                totalScore = scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType().equals(ScoreType.USER_PARAMETER)) {
                channelStatisticInfos.add(
                    new ChannelStatisticInfo(scoreSummary.getName()
                        , scoreSummary.getScore()));
            }
        }

        for(ScoreSummary scoreSummary : scoreSummaryRepository.findByCutOffHistoryAndAgent(beforeLastCutOff, agent)) {
            if(scoreSummary.getScoreType().equals(ScoreType.USER_TOTAL)) {
                totalScoreDiff = totalScore - scoreSummary.getScore();
            }
            if(scoreSummary.getScoreType().equals(ScoreType.USER_PARAMETER)) {
                for(ChannelStatisticInfo channelStatisticInfo : channelStatisticInfos) {
                    Parameter parameter = parameterRepository.findOne(scoreSummary.getFkId());
                    String parameterName = parameter.getName();
                    if(channelStatisticInfo.getName().equalsIgnoreCase(parameterName)) {
                        channelDiff = channelStatisticInfo.getScore() - scoreSummary.getScore();
                        channelStatisticInfo.setDiffScore(channelDiff);
                    }
                }
            }
        }

        return new StatisticInfoResponse(totalScore, totalScoreDiff, channelStatisticInfos);
    }

    public List<AgentReviewNoteResponse> getAgentNote(User agent) {
        List<AgentReviewNoteResponse> agentReviewNoteResponses = new ArrayList<>();

        CutOffHistory cutOffLast = cutOffRepository.findByEndCutOffIsNull();
        CutOffHistory cutOff = cutOffRepository.findByEndCutOff(cutOffLast.getBeginCutOff());

        List<Review> reviewList = reviewRepository.findByAgentAndCutOffHistory(agent, cutOff);

        for (Review review : reviewList) {
            for (DetailReview detailReview : detailReviewRepository.findByReview(review)) {
                if(detailReview.getNote() == null) {
                    continue;
                }
                String categoryName = review.getParameter().getName()+"-"+detailReview.getCategory().getName();
                Float detailReviewScore = detailReview.getScore();
                String note = detailReview.getNote();
                agentReviewNoteResponses.add(new AgentReviewNoteResponse(categoryName, detailReviewScore, note));
            }
        }
        return agentReviewNoteResponses;
    }
}
