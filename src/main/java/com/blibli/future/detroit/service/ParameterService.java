package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.Category;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.Parameter;
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

    public Parameter createParameter(NewParameterRequest request, Long categoryId) {
        Parameter newParameter = new Parameter();
        newParameter.setName(request.getName());
        newParameter.setDescription(request.getDescription());
        newParameter.setWeight(request.getWeight());
        Category newCategory = categoryService.getOneCategory(categoryId);
        newParameter.setCategory(newCategory);
        parameterRepository.save(newParameter);

        return newParameter;
    }

    public boolean deleteParameter(Long parameterId) {
        // TODO change to soft delete
        parameterRepository.delete(parameterId);
        return true;
    }

    public boolean batchUpdateParameter(SimpleListRequest<Parameter> request, Long categoryId) throws WeightPercentageNotValid {
        List<Parameter> parameterList = new ArrayList<>();
        for(Parameter input: request.getList()) {
            Parameter parameter = new Parameter();
            parameter.setId(input.getId()); //Tambah SetId di model user dan parameter, newRequest juga
            parameter.setCategory(input.getCategory());
            parameter.setName(input.getName());
            parameter.setDescription(input.getDescription());
            parameter.setWeight(input.getWeight());
            parameterList.add(parameter);
        }
        boolean isValidUpdate = isAllParameterHaveBalancedWeight(categoryId);
        if (!isValidUpdate) {
            throw new WeightPercentageNotValid("Total percentage of all weight in parameter is not 100%");
        }
        parameterRepository.save(parameterList);
        return true;
    }

    public boolean isAllParameterHaveBalancedWeight(Long categoryId) {
        float sum = 0;
        Category category = categoryService.getOneCategory(categoryId);
        List<Parameter> parameters = category.getParameters();
        for(Parameter parameter: parameters) {
            sum += parameter.getWeight();
        }
        return sum == 100;
    }
}
