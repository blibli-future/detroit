package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.service.CategoryService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {
    public static final String BASE_PATH = Constant.API_PATH_V1 + "/categories";
    public static final String GET_ALL_CATEGORY = BASE_PATH;
    public static final String CREATE_CATEGORY = BASE_PATH;
    public static final String DELETE_CATEGORY = BASE_PATH + "/{categoryId}";
    public static final String GET_ONE_CATEGORY = BASE_PATH + "/{categoryId}";
    public static final String BATCH_UPDATE_CATEGORY = BASE_PATH;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = GET_ALL_CATEGORY, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestListResponse<Category> getAllCategories() {
        List<Category> allCategory = categoryService.getAllCategory();
        return new BaseRestListResponse<>(allCategory);
    }

    @PostMapping(value = CREATE_CATEGORY, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse createCategory(@RequestBody NewCategoryRequest request) {
        categoryService.createCategory(request);
        return new BaseRestResponse();
    }

    @DeleteMapping(value = DELETE_CATEGORY, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new BaseRestResponse();
    }

    @GetMapping(value = GET_ONE_CATEGORY, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse<Category> getOneCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getOneCategory(categoryId);
        return new BaseRestResponse<>(category);
    }

    @PatchMapping(value = BATCH_UPDATE_CATEGORY, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse batchUpdateCategory(@RequestBody SimpleListRequest<Category> request) {
        try {
            assert categoryService.batchUpdateCategory(request);
            return new BaseRestResponse();
        }
        catch (WeightPercentageNotValid e) {
            return new BaseRestResponse(false, e.getMessage(), e.getClass().getSimpleName());
        }
        catch (AssertionError e) {
            return new BaseRestResponse(false, e.getMessage(), e.getClass().getSimpleName());
        }
    }
}
