package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getOneCategory(long categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public Category createCategory(NewCategoryRequest request) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        newCategory.setActive(request.isActive());
        newCategory.setDescription(request.getDescription());
        newCategory.setWeight(0);
        newCategory.setBulkStatus(request.isBulkStatus());
        categoryRepository.save(newCategory);

        return newCategory;
    }

    public boolean deleteCategory(Long categoryId) {
        // TODO change to soft delete
        categoryRepository.delete(categoryId);
        return true;
    }

    /**
     * Update all the categories at the same time. A valid update
     * *must* have sum 100% in weight across all the categories.
     * @param request
     * @return edit success status
     */
    public boolean batchUpdateCategory(SimpleListRequest<Category> request) throws WeightPercentageNotValid {
        // TODO is better/more efficient query required?
        List<Category> categoryList = new ArrayList<>();
        for(Category input: request.getList()) {
            Category category = categoryRepository.findOne(input.getId());
            category.setWeight(input.getWeight());
            category.setName(input.getName());
            category.setDescription(input.getDescription());
            categoryList.add(category);
        }
        boolean isValidUpdate = isAllCategoryHaveBalancedWeight();
        if (!isValidUpdate) {
            throw new WeightPercentageNotValid("Total percentage of all weight in category is not 100%");
        }
        categoryRepository.save(categoryList);
        return true;
    }

    /**
     * Check if the combined weight of all category is exactly 100%
     * @return
     */
    public boolean isAllCategoryHaveBalancedWeight() {
        // TODO is better/more efficient query required?
        float sum = 0;
        for(Category category: getAllCategory()) {
            sum += category.getWeight();
        }

        return sum == 100;
    }


}
