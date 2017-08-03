package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.dto.BatchUpdateParameterDto;
import com.blibli.future.detroit.model.dto.ParameterDetailDto;
import com.blibli.future.detroit.model.dto.ParameterListDto;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.ParameterService;
import com.blibli.future.detroit.util.Constant;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@Controller
public class ParameterController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/parameters";
    public static final String GET_ALL_PARAMETER = BASE_PATH;
    public static final String CREATE_PARAMETER = BASE_PATH;
    public static final String DELETE_PARAMETER = BASE_PATH + "/{parameterId}";
    public static final String GET_ONE_PARAMETER = BASE_PATH + "/{parameterId}";
    public static final String BATCH_UPDATE_PARAMETER = BASE_PATH + "/batch-update";

    @Autowired
    private ParameterService parameterService;

    @GetMapping(value = GET_ALL_PARAMETER, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestListResponse<ParameterListDto> getAllParameters() {
        Type listType = new TypeToken<List<ParameterListDto>>() {}.getType();
        List<ParameterListDto> allParameter = new ModelMapper().map(parameterService.getAllParameter(), listType);
        return new BaseRestListResponse<>(allParameter);
    }

    @PostMapping(value = CREATE_PARAMETER, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse createParameter(@RequestBody NewParameterRequest request) {
        parameterService.createParameter(request);
        return new BaseRestResponse();
    }

    @DeleteMapping(value = DELETE_PARAMETER, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse deleteParameter(@PathVariable Long parameterId) {
        parameterService.deleteParameter(parameterId);
        return new BaseRestResponse();
    }

    @GetMapping(value = GET_ONE_PARAMETER, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse<ParameterDetailDto> getOneParameter(@PathVariable Long parameterId) {
        Parameter parameter = parameterService.getOneParameter(parameterId);
        return new BaseRestResponse<>(new ParameterDetailDto(parameter));
    }

    @PatchMapping(value = BATCH_UPDATE_PARAMETER, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse batchUpdateParameter(
            @RequestBody List<BatchUpdateParameterDto> request) {
        try {
            parameterService.batchUpdateParameter(request);
            return new BaseRestResponse();
        }
        catch (WeightPercentageNotValid e) {
            return new BaseRestResponse(false, e.getMessage(), e.getClass().getSimpleName());
        }
    }
}
