package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.ParameterService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ParameterController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/categories";
    public static final String END_PATH = "/parameters";
    public static final String CREATE_PARAMETER = BASE_PATH + "/{categoryId}" + END_PATH;
    public static final String DELETE_PARAMETER = BASE_PATH + "/{categoryId}" + END_PATH + "/{parameterId}";
    public static final String BATCH_UPDATE_PARAMETER = BASE_PATH + "/{categoryId}" + END_PATH;

    @Autowired
    private ParameterService parameterService;

    @PostMapping(value = CREATE_PARAMETER, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse createParameter(@RequestBody NewParameterRequest request, @PathVariable Long categoryId) {
        parameterService.createParameter(request, categoryId);
        return new BaseRestResponse();
    }

    @PatchMapping(value = BATCH_UPDATE_PARAMETER, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse batchUpdateParameter(@RequestBody SimpleListRequest<Category> request,
                                                 @PathVariable Long categoryId) {
        try{
            parameterService.batchUpdateParameter(request, categoryId);
            return new BaseRestResponse();
        } catch (WeightPercentageNotValid e) {
            return new BaseRestResponse(false, e.getMessage(), e.getClass().getSimpleName());
        }
    }

    @DeleteMapping(value = DELETE_PARAMETER, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse<Category> deleteParameter(@PathVariable Long categoryId, @PathVariable Long parameterId) {
        parameterService.deleteParameter(parameterId);
        return new BaseRestResponse();
    }
}
