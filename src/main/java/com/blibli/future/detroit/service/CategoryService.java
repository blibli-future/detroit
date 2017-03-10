package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.request.NewCategoryRequest;
import com.blibli.future.detroit.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category createCategory(NewCategoryRequest request) {
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        newCategory.setActive(request.isActive());
        newCategory.setDescription(request.getDescription());
        newCategory.setWeight(request.getWeight());
        newCategory.setBulkStatus(request.isBulkStatus());
        categoryRepository.save(newCategory);

        return newCategory;
    }

    public boolean deleteCategory(Long categoryId) {
        categoryRepository.delete(categoryId);
        return true;
    }
}
