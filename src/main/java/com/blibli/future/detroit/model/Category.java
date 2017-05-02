package com.blibli.future.detroit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Category {


    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Float weight;
    @ManyToOne
    private Parameter parameter;

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

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category Category = (Category) o;

        if (!id.equals(Category.id)) return false;
        if (!name.equals(Category.name)) return false;
        if (description != null ? !description.equals(Category.description) : Category.description != null)
            return false;
        if (!weight.equals(Category.weight)) return false;
        return parameter.equals(Category.parameter);
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
