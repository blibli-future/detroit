package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.CategoryService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/parameters" + "/{parameterId}" + "/categories";
    public static final String CREATE_CATEGORY = BASE_PATH;
    public static final String DELETE_CATEGORY = BASE_PATH + "/{categoryId}";
    public static final String BATCH_UPDATE_CATEGORY = BASE_PATH;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = CREATE_CATEGORY, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse createCategory(@RequestBody NewCategoryRequest request, @PathVariable Long parameterId) {
        categoryService.createCategory(request, parameterId);
        return new BaseRestResponse();
    }

    @PatchMapping(value = BATCH_UPDATE_CATEGORY, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse batchUpdateCategory(@RequestBody SimpleListRequest<Category> request,
                                                 @PathVariable Long parameterId) {
        try{
            categoryService.batchUpdateCategory(request, parameterId);
            return new BaseRestResponse();
        } catch (WeightPercentageNotValid e) {
            return new BaseRestResponse(false, e.getMessage(), e.getClass().getSimpleName());
        }
    }

    @DeleteMapping(value = DELETE_CATEGORY, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse<Category> deleteCategory(@PathVariable Long parameterId, @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new BaseRestResponse();
    }
}
