package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.StatisticInfo;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.model.response.StatisticDiagramResponse;
import com.blibli.future.detroit.service.StatisticService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class StatisticController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/statistic";
    public static final String GET_ALL_STATISTIC_DATA = BASE_PATH;
    public static final String GET_ALL_STATISTIC_INFO = BASE_PATH + "/info";

    @Autowired
    StatisticService statisticService;

    @GetMapping(GET_ALL_STATISTIC_DATA)
    public BaseRestListResponse<StatisticDiagramResponse> getAllStatisticData() {
        return new BaseRestListResponse<>(statisticService.getCurrentAllStatisticDiagram());
    }

    @GetMapping(GET_ALL_STATISTIC_INFO)
    public BaseRestResponse<StatisticInfo> getAllStatisticInfo() {
        return new BaseRestResponse<>(statisticService.getCurrentStatisticInfo());
    }
}
