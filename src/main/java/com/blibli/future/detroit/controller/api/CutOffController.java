package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.CutOffHistory;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.model.response.CutOffResponse;
import com.blibli.future.detroit.service.CutOffService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CutOffController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/cutoff";
    public static final String GET_ALL_CUTOFF_HISTORY = BASE_PATH;
    public static final String GET_CURRENT_CUTOFF_HISTORY = BASE_PATH + "/current";

    @Autowired
    private CutOffService cutOffService;

    @GetMapping(GET_ALL_CUTOFF_HISTORY)
    public BaseRestListResponse<CutOffResponse> getAllCutOffHistory() {
        List<CutOffResponse> allCutOffResponse = cutOffService.getAllCutOff();
        return new BaseRestListResponse<>(allCutOffResponse);
    }

    @GetMapping(GET_CURRENT_CUTOFF_HISTORY)
    public BaseRestResponse<CutOffResponse> getCurrentCutOffHistory() {
        CutOffResponse cutOffResponse = cutOffService.getCurrentCutOff();
        return new BaseRestResponse<>(cutOffResponse);
    }
}
