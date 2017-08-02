package com.blibli.future.detroit.service;

import com.blibli.future.detroit.model.AgentChannel;
import com.blibli.future.detroit.model.Parameter;
import com.blibli.future.detroit.model.Exception.WeightPercentageNotValid;
import com.blibli.future.detroit.model.dto.BatchUpdateParameterDto;
import com.blibli.future.detroit.model.request.NewParameterRequest;
import com.blibli.future.detroit.model.request.SimpleListRequest;
import com.blibli.future.detroit.repository.AgentChannelRepository;
import com.blibli.future.detroit.repository.ParameterRepository;
import com.blibli.future.detroit.util.configuration.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;
    @Autowired
    private AgentChannelRepository agentChannelRepository;
    @Autowired
    Converter modelMapper;

    public List<Parameter> getAllParameter() {
        return parameterRepository.findAll();
    }

    public Parameter getOneParameter(long parameterId) {
        return parameterRepository.findOne(parameterId);
    }

    public Parameter createParameter(NewParameterRequest request) {
        Parameter newParameter = modelMapper.modelMapper()
            .map(request, Parameter.class);
        newParameter.setAgentChannel(
            agentChannelRepository.getOne(request.getAgentChannelId()));
        parameterRepository.saveAndFlush(newParameter);

        return newParameter;
    }

    public boolean deleteParameter(Long parameterId) {
        // TODO change to soft delete
        parameterRepository.delete(parameterId);
        return true;
    }

    /**
     * Update all the categories at the same time.
     *
     * A valid update *must* have sum 100% in weight across all the categories.
     *
     * @param parameterList param
     * @return edit success status
     */
    public boolean batchUpdateParameter(
            List<BatchUpdateParameterDto> parameterList)
            throws WeightPercentageNotValid {
        System.out.println(parameterList);
        List<Parameter> updatedParameterList = new ArrayList<>();
        for (BatchUpdateParameterDto input: parameterList) {
            Parameter parameter = parameterRepository.getOne(input.getParameterId());
            parameter.setWeight(input.getWeight());
            updatedParameterList.add(parameter);
        }
        parameterRepository.save(updatedParameterList);
        isAllParameterHaveBalancedWeight();
        return true;
    }

    /**
     * Check if the combined weight of all parameter for all channel is ~~exactly~~
     * sorta close of 100%.
     *
     * @return boolean
     */
    private boolean isAllParameterHaveBalancedWeight() throws WeightPercentageNotValid {
        for (AgentChannel channel: agentChannelRepository.findAll()) {
            float sum = 0;
            for(Parameter parameter : parameterRepository.findByAgentChannel(channel)) {
                sum += parameter.getWeight();
            }
            // sorta close
            if (Math.abs(sum - 100) > 0.001) {
                throw new WeightPercentageNotValid(
                    String.format("Total percentage of all weight in channel %s is not 100%%", channel.getName()));
            }
        }
        return true;
    }


}
