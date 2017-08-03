package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.Parameter;

import java.util.List;
import java.util.stream.Collectors;

public class ParameterDetailDto {
    Long id;
    String name;
    Float weight;
    String description;
    Long agentChannelId;
    List<CategoryDetailDto> categories;

    public ParameterDetailDto() {
    }

    public ParameterDetailDto(Parameter parameter) {
        id = parameter.getId();
        name = parameter.getName();
        weight = parameter.getWeight();
        description = parameter.getDescription();
        agentChannelId = parameter.getAgentChannel().getId();
        categories = parameter.getCategories().stream()
                              .map(CategoryDetailDto::new)
                              .collect(Collectors.toList());
    }

    public Parameter convertToParameter(Parameter parameter) {
        parameter.setName(name);
        parameter.setWeight(weight);
        parameter.setDescription(description);
        return parameter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAgentChannelId() {
        return agentChannelId;
    }

    public void setAgentChannelId(Long agentChannelId) {
        this.agentChannelId = agentChannelId;
    }

    public List<CategoryDetailDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDetailDto> categories) {
        this.categories = categories;
    }
}
