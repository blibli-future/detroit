package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.StatisticInfo;
import com.blibli.future.detroit.model.StatisticInfoIndividual;
import com.blibli.future.detroit.model.response.*;
import com.blibli.future.detroit.service.StatisticService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class StatisticController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/statistic";
    public static final String GET_ALL_STATISTIC_DATA = BASE_PATH;
    public static final String GET_ALL_STATISTIC_INFO = BASE_PATH + "/info";
    public static final String GET_INDIVIDUAL_STATISTIC_INFO = BASE_PATH + "/info" + "/{agentId}";
    public static final String GET_INDIVIDUAL_STATISTIC_DIAGRAM = BASE_PATH + "/{agentId}";
    public static final String GET_INDIVIDUAL_REVIEW_NOTE = BASE_PATH + "/review/note" + "/{agentId}";

    @Autowired
    StatisticService statisticService;

    @GetMapping(GET_ALL_STATISTIC_DATA)
    public BaseRestResponse<StatisticDiagramResponseNew> getAllStatisticData() {
        return new BaseRestResponse<>(statisticService.getCurrentAllStatisticDiagram());
    }

    @GetMapping(GET_ALL_STATISTIC_INFO)
    public BaseRestResponse<StatisticInfo> getAllStatisticInfo() {
        return new BaseRestResponse<>(statisticService.getCurrentStatisticInfo());
    }

    @GetMapping(GET_INDIVIDUAL_STATISTIC_INFO)
    public BaseRestResponse<StatisticInfoIndividual> getIndividualStatisticInfo(@PathVariable Long agentId) {
        return new BaseRestResponse<>(statisticService.getIndividualStatisticInfo(agentId));
    }

    @GetMapping(GET_INDIVIDUAL_STATISTIC_DIAGRAM)
    public BaseRestListResponse<StatisticDiagramIndividualResponse> getIndividualStatisticDiagram(@PathVariable Long agentId) {
        return new BaseRestListResponse<>(statisticService.getIndividualStatisticDiagram(agentId));
    }

    @GetMapping(GET_INDIVIDUAL_REVIEW_NOTE)
    public BaseRestListResponse<AgentReviewNoteResponse> getIndividualReviewNote(@PathVariable Long agentId) {
        return new BaseRestListResponse<>(statisticService.getIndividualReviewNote(agentId));
    }
}
