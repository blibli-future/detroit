package com.blibli.future.detroit.controller.api;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.response.BaseRestListResponse;
import com.blibli.future.detroit.model.response.BaseRestResponse;
import com.blibli.future.detroit.repository.CategoryRepository;
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
    private CategoryRepository categoryRepository;

    @GetMapping(value = GET_ALL_CATEGORY, produces = "application/json")
    @ResponseBody
    public BaseRestListResponse<Category> getCategories() {
        return new BaseRestListResponse<>(categoryRepository.findAll());
    }

    @PostMapping(value = CREATE_CATEGORY, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public BaseRestResponse postCategories(@RequestBody NewCategoryRequest request) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        newCategory.setActive(request.isActive());
        newCategory.setDescription(request.getDescription());
        newCategory.setWeight(request.getWeight());
        newCategory.setBulkStatus(request.isBulkStatus());

        categoryRepository.save(newCategory);

        return new BaseRestResponse();
    }

    @DeleteMapping(value = DELETE_CATEGORY, produces = "application/json")
    @ResponseBody
    public BaseRestResponse deleteCategories(@PathVariable Long categoryId) {
        categoryRepository.delete(categoryId);
        return new BaseRestResponse();
    }
}
