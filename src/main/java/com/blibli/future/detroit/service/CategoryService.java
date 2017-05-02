package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParameterService parameterService;

    public Category createCategory(NewCategoryRequest request, Long parameterId) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        newCategory.setDescription(request.getDescription());
        newCategory.setWeight(request.getWeight());
        Parameter newParameter = parameterService.getOneParameter(parameterId);
        newCategory.setParameter(newParameter);
        categoryRepository.saveAndFlush(newCategory);

        return newCategory;
    }

    public boolean deleteCategory(Long categoryId) {
        // TODO change to soft delete
        categoryRepository.delete(categoryId);
        return true;
    }

    public boolean batchUpdateCategory(SimpleListRequest<Category> request, Long parameterId) throws WeightPercentageNotValid {
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
