package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.repository.CategoryRepository;
import com.blibli.future.detroit.util.configuration.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    Converter modelMapper;

    public Category createCategory(NewCategoryRequest request) {
        Category newCategory = modelMapper.modelMapper()
            .map(request, Category.class);
        categoryRepository.saveAndFlush(newCategory);
        return newCategory;
    }

    public boolean deleteCategory(Long categoryId) {
        // TODO change to soft delete
        categoryRepository.delete(categoryId);
        return true;
    }

    public boolean batchUpdateCategory(SimpleListRequest<NewCategoryRequest> request, Long parameterId) throws WeightPercentageNotValid {
        List<Category> categoryList = new ArrayList<>();
        for(NewCategoryRequest input: request.getList()) {
            Category Category = modelMapper.modelMapper()
                .map(input, Category.class);
            categoryList.add(Category);
        }
        boolean isValidUpdate = isAllCategoryHaveBalancedWeight(parameterId);
        if (!isValidUpdate) {
            throw new WeightPercentageNotValid("Total percentage of all weight in category is not 100%");
        }
        categoryRepository.save(categoryList);
        return true;
    }

    public boolean isAllCategoryHaveBalancedWeight(Long parameterId) {
        float sum = 0;
        Parameter parameter = parameterService.getOneParameter(parameterId);
        List<Category> categories = parameter.getCategories();
        for(Category category : categories) {
            sum += category.getWeight();
        }
        return sum == 100;
    }
}
