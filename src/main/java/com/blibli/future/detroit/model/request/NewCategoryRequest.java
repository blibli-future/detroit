package com.blibli.future.detroit.model.request;

import com.blibli.future.detroit.model.Parameter;

import java.io.Serializable;

public class NewCategoryRequest implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Float weight;
    private Parameter parameter;

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewCategoryRequest that = (NewCategoryRequest) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!weight.equals(that.weight)) return false;
        return parameter.equals(that.parameter);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + weight.hashCode();
        result = 31 * result + parameter.hashCode();
        return result;
    }
}
