package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.Category;
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

    public List<Parameter> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Parameter getOneCategory(long categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public Parameter createCategory(NewCategoryRequest request) {
        Parameter newParameter = new Parameter();
        newParameter.setName(request.getName());
        newParameter.setActive(request.isActive());
        newParameter.setDescription(request.getDescription());
        newParameter.setWeight(0f);
        newParameter.setBulkStatus(request.isBulkStatus());
        categoryRepository.save(newParameter);

        return newParameter;
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
    public boolean batchUpdateCategory(SimpleListRequest<Parameter> request) throws WeightPercentageNotValid {
        // TODO is better/more efficient query required?
        List<Parameter> parameterList = new ArrayList<>();
        for(Parameter input: request.getList()) {
            Parameter parameter = categoryRepository.findOne(input.getId());
            parameter.setWeight(input.getWeight());
            parameter.setName(input.getName());
            parameter.setDescription(input.getDescription());
            parameterList.add(parameter);
        }
        boolean isValidUpdate = isAllCategoryHaveBalancedWeight();
        if (!isValidUpdate) {
            throw new WeightPercentageNotValid("Total percentage of all weight in category is not 100%");
        }
        categoryRepository.save(parameterList);
        return true;
    }

    /**
     * Check if the combined weight of all category is exactly 100%
     * @return
     */
    public boolean isAllCategoryHaveBalancedWeight() {
        // TODO is better/more efficient query required?
        float sum = 0;
        for(Parameter parameter : getAllCategory()) {
            sum += parameter.getWeight();
        }

        return sum == 100;
    }


}
