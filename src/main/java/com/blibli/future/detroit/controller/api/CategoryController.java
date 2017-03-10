package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.repository.CategoryRepository;
import com.blibli.future.detroit.service.CategoryService;
import com.blibli.future.detroit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    public static final String BASE_PATH = "/api/v1/categories";
    public static final String GET_ALL_CATEGORY = BASE_PATH;
    public static final String CREATE_CATEGORY = BASE_PATH;
    public static final String DELETE_CATEGORY = BASE_PATH + "/{categoryId}";

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = GET_ALL_CATEGORY, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestListResponse<Category> getCategories() {
        return new BaseRestListResponse<>(categoryService.getAllCategory());
    }

    @PostMapping(value = CREATE_CATEGORY, produces = Constant.API_MEDIA_TYPE, consumes = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse postCategories(@RequestBody NewCategoryRequest request) {
        Category newCategory = categoryService.createCategory(request);
        return new BaseRestResponse();
    }

    @DeleteMapping(value = DELETE_CATEGORY, produces = Constant.API_MEDIA_TYPE)
    @ResponseBody
    public BaseRestResponse deleteCategories(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new BaseRestResponse();
    }
}
