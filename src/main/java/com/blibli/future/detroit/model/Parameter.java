package com.blibli.future.detroit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Parameter {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Float weight;
    private boolean bulkStatus = false;
    private boolean isActive = true;
    @OneToMany
    private List<Category> Categories;

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

    public boolean isBulkStatus() {
        return bulkStatus;
    }

    public void setBulkStatus(boolean bulkStatus) {
        this.bulkStatus = bulkStatus;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Category> getCategories() {
        return Categories;
    }

    public void setCategories(List<Category> categories) {
        this.Categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (Float.compare(parameter.weight, weight) != 0) return false;
        if (bulkStatus != parameter.bulkStatus) return false;
        if (isActive != parameter.isActive) return false;
        if (!id.equals(parameter.id)) return false;
        if (!name.equals(parameter.name)) return false;
        if (description != null ? !description.equals(parameter.description) : parameter.description != null)
            return false;
        return Categories != null ? Categories.equals(parameter.Categories) : parameter.Categories == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (weight != +0.0f ? Float.floatToIntBits(weight) : 0);
        result = 31 * result + (bulkStatus ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (Categories != null ? Categories.hashCode() : 0);
        return result;
    }
}
