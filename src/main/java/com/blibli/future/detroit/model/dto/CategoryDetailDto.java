package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.Category;

public class CategoryDetailDto {
    Long id;
    String name;
    Float weight;
    String description;

    public CategoryDetailDto() {
    }

    public CategoryDetailDto(Category category) {
        id = category.getId();
        name = category.getName();
        weight = category.getWeight();
        description = category.getDescription();
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

    @Override
    public String toString() {
        return "CategoryDetailDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", weight=" + weight +
            ", description='" + description + '\'' +
            '}';
    }
}
