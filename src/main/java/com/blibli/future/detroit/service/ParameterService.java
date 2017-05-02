package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;
    private CategoryService categoryService;

    public Category createParameter(NewParameterRequest request, Long categoryId) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        newCategory.setDescription(request.getDescription());
        newCategory.setWeight(request.getWeight());
        Parameter newParameter = categoryService.getOneCategory(categoryId);
        newCategory.setParameter(newParameter);
        parameterRepository.save(newCategory);

        return newCategory;
    }

    public boolean deleteParameter(Long parameterId) {
        // TODO change to soft delete
        parameterRepository.delete(parameterId);
        return true;
    }

    public boolean batchUpdateParameter(SimpleListRequest<Category> request, Long categoryId) throws WeightPercentageNotValid {
        List<Category> categoryList = new ArrayList<>();
        for(Category input: request.getList()) {
            Category Category = new Category();
            Category.setId(input.getId()); //Tambah SetId di model user dan Category, newRequest juga
            Category.setParameter(input.getParameter());
            Category.setName(input.getName());
            Category.setDescription(input.getDescription());
            Category.setWeight(input.getWeight());
            categoryList.add(Category);
        }
        boolean isValidUpdate = isAllParameterHaveBalancedWeight(categoryId);
        if (!isValidUpdate) {
            throw new WeightPercentageNotValid("Total percentage of all weight in parameter is not 100%");
        }
        parameterRepository.save(categoryList);
        return true;
    }

    public boolean isAllParameterHaveBalancedWeight(Long categoryId) {
        float sum = 0;
        Parameter parameter = categoryService.getOneCategory(categoryId);
        List<Category> Categories = parameter.getCategories();
        for(Category Category : Categories) {
            sum += Category.getWeight();
        }
        return sum == 100;
    }
}
