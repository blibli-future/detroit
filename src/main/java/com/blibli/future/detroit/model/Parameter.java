package com.blibli.future.detroit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Parameter {


    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Float weight;
    @ManyToOne
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (!id.equals(parameter.id)) return false;
        if (!name.equals(parameter.name)) return false;
        if (description != null ? !description.equals(parameter.description) : parameter.description != null)
            return false;
        if (!weight.equals(parameter.weight)) return false;
        return category.equals(parameter.category);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + weight.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}
