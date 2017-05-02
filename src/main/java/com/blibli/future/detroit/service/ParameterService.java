package com.blibli.future.detroit.service;

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

    public List<Parameter> getAllParameter() {
        return parameterRepository.findAll();
    }

    public Parameter getOneParameter(long parameterId) {
        return parameterRepository.findOne(parameterId);
    }

    public Parameter createParameter(NewParameterRequest request) {
        Parameter newParameter = new Parameter();
        newParameter.setName(request.getName());
        newParameter.setActive(request.isActive());
        newParameter.setDescription(request.getDescription());
        newParameter.setWeight(0f);
        newParameter.setBulkStatus(request.isBulkStatus());
        parameterRepository.save(newParameter);

        return newParameter;
    }

    public boolean deleteParameter(Long parameterId) {
        // TODO change to soft delete
        parameterRepository.delete(parameterId);
        return true;
    }

    /**
     * Update all the categories at the same time. A valid update
     * *must* have sum 100% in weight across all the categories.
     * @param request
     * @return edit success status
     */
    public boolean batchUpdateParameter(SimpleListRequest<Parameter> request) throws WeightPercentageNotValid {
        // TODO is better/more efficient query required?
        List<Parameter> parameterList = new ArrayList<>();
        for(Parameter input: request.getList()) {
            Parameter parameter = parameterRepository.findOne(input.getId());
            parameter.setWeight(input.getWeight());
            parameter.setName(input.getName());
            parameter.setDescription(input.getDescription());
            parameterList.add(parameter);
        }
        boolean isValidUpdate = isAllParameterHaveBalancedWeight();
        if (!isValidUpdate) {
            throw new WeightPercentageNotValid("Total percentage of all weight in parameter is not 100%");
        }
        parameterRepository.save(parameterList);
        return true;
    }

    /**
     * Check if the combined weight of all parameter is exactly 100%
     * @return
     */
    public boolean isAllParameterHaveBalancedWeight() {
        // TODO is better/more efficient query required?
        float sum = 0;
        for(Parameter parameter : getAllParameter()) {
            sum += parameter.getWeight();
        }

        return sum == 100;
    }


}
