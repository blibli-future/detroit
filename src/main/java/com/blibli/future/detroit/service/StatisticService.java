package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.*;
import com.blibli.future.detroit.model.response.StatisticDiagramResponse;
import com.blibli.future.detroit.repository.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class StatisticService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CutOffRepository cutOffRepository;

    public List<StatisticDiagramResponse> getCurrentAllStatisticDiagram() {
        List<StatisticDiagramResponse> statisticDiagramResponses = new ArrayList<>();

        HashMap<String, Float> parameterAvgMap = new HashMap<>();
        HashMap<String, Integer> parameterCountMap = new HashMap<>();
        HashMap<String, Float> categoryAvgMap = new HashMap<>();
        HashMap<String, Integer> categoryCountMap = new HashMap<>();
        LocalDate now = new LocalDate();

        for(CutOffHistory cutOffHistory : cutOffRepository.findAll()) {
            if(cutOffHistory.getEndInISOFormat() == null) {
                continue;
            } else {
                if(cutOffHistory.getEnd().getYear() != now.getYear()) {
                    continue;
                }
                for(Review review : cutOffHistory.getReviews()) {
                    String parameter = review.getParameter().getName();
                    Integer currentCountParameter = parameterCountMap.getOrDefault(parameter, 0);
                    parameterCountMap.put(parameter, currentCountParameter+1);

                    Float currentParameter = review.getScore();

                    for(DetailReview detailReview : review.getDetailReview()) {
                        String category = detailReview.getCategory().getName();
                        Integer currentCount = categoryCountMap.getOrDefault(category, 0);
                        categoryCountMap.put(category, currentCount+1);

                        Float currentAvg = categoryAvgMap.getOrDefault(category, 0f);
                        Float nextAvg = ((currentAvg*currentCount)+detailReview.getScore()) / (currentCount+1);
                        categoryAvgMap.put(category, nextAvg);
                    }
                    Float currentAvgParameter = parameterAvgMap.getOrDefault(parameter, 0f);
                    Float nextAvgParameter = ((currentAvgParameter*currentCountParameter)+currentParameter) / (currentCountParameter+1);
                    parameterAvgMap.put(parameter, nextAvgParameter);
                }
                for (Map.Entry<String, Float> entry : parameterAvgMap.entrySet()) {
                    String key = entry.getKey();
                    Float value = entry.getValue();

                    StatisticDiagramResponse statisticDiagramResponse = new StatisticDiagramResponse();
                    statisticDiagramResponse.setParameter(key);
                    statisticDiagramResponse.addValue(value, cutOffHistory.getEnd());

                    for (Map.Entry<String, Float> entryCategory : categoryAvgMap.entrySet()) {
                        String categoryName = entryCategory.getKey();
                        Float categoryValue = entryCategory.getValue();

                        statisticDiagramResponse.addCategory(categoryName, categoryValue, cutOffHistory.getEnd());
                    }

                    statisticDiagramResponses.add(statisticDiagramResponse);
                }
            }
        }
        return statisticDiagramResponses;
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

        CutOffHistory cutOffHistory = cutOffRepository.findByEndIsNull();
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
}
