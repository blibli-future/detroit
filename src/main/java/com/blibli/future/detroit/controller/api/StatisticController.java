package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Exception.NotAuthorizedException;
import com.blibli.future.detroit.model.StatisticInfo;
import com.blibli.future.detroit.model.StatisticInfoIndividual;
import com.blibli.future.detroit.model.User;
import com.blibli.future.detroit.model.response.*;
import com.blibli.future.detroit.service.AuthenticationService;
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
    public static final String GET_AGENT_DIAGRAM_REPORT = BASE_PATH + "/agent-report/diagram";
    public static final String GET_AGENT_INFO_REPORT = BASE_PATH + "/agent-report/info";
    public static final String GET_AGENT_NOTE_REPORT = BASE_PATH + "/agent-report/note";

    @Autowired
    StatisticService statisticService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(GET_ALL_STATISTIC_DATA)
    public BaseRestResponse<StatisticDiagramResponseNew> getAllStatisticData() {
        return new BaseRestResponse<>(statisticService.getCurrentAllStatisticDiagram());
    }

    @GetMapping(GET_ALL_STATISTIC_INFO)
    public BaseRestResponse<StatisticInfoResponse> getAllStatisticInfo() {
        return new BaseRestResponse<>(statisticService.getCurrentStatisticInfo());
    }

    @GetMapping(GET_INDIVIDUAL_STATISTIC_INFO)
    public BaseRestResponse<StatisticInfoResponse> getIndividualStatisticInfo(@PathVariable Long agentId) {
        return new BaseRestResponse<>(statisticService.getIndividualStatisticInfo(agentId));
    }

    @GetMapping(GET_INDIVIDUAL_STATISTIC_DIAGRAM)
    public BaseRestResponse<StatisticDiagramIndividualResponse> getIndividualStatisticDiagram(@PathVariable Long agentId) {
        return new BaseRestResponse<>(statisticService.getIndividualStatisticDiagram(agentId));
    }

    @GetMapping(GET_INDIVIDUAL_REVIEW_NOTE)
    public BaseRestListResponse<AgentReviewNoteResponse> getIndividualReviewNote(@PathVariable Long agentId) {
        return new BaseRestListResponse<>(statisticService.getIndividualReviewNote(agentId));
    }

    @GetMapping(GET_AGENT_DIAGRAM_REPORT)
    public BaseRestResponse<StatisticDiagramIndividualResponse> getAgentDiagram() {
        User currentUser = authenticationService.getCurrentUser();
        return new BaseRestResponse<>(statisticService.getAgentDiagram(currentUser));
    }

    @GetMapping(GET_AGENT_INFO_REPORT)
    public BaseRestResponse<StatisticInfoResponse> getAgentInfo() {
        User currentUser = authenticationService.getCurrentUser();
        return new BaseRestResponse<>(statisticService.getAgentInfo(currentUser));
    }

    @GetMapping(GET_AGENT_NOTE_REPORT)
    public BaseRestListResponse<AgentReviewNoteResponse> getAgentNote() {
        User currentUser = authenticationService.getCurrentUser();
        return new BaseRestListResponse<>(statisticService.getAgentNote(currentUser));
    }
}
