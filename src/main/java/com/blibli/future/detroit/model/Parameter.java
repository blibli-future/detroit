package com.blibli.future.detroit.model;

import javax.persistence.*;
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
    @OneToMany(fetch=FetchType.EAGER)
    private List<Category> Categories;
    @ManyToOne
    private AgentPosition agentPosition;

    public int getTest() {
        return this.Categories.size();
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
        Categories = categories;
    }

    public AgentPosition getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(AgentPosition agentPosition) {
        this.agentPosition = agentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (bulkStatus != parameter.bulkStatus) return false;
        if (isActive != parameter.isActive) return false;
        if (id != null ? !id.equals(parameter.id) : parameter.id != null) return false;
        if (name != null ? !name.equals(parameter.name) : parameter.name != null) return false;
        if (description != null ? !description.equals(parameter.description) : parameter.description != null)
            return false;
        if (weight != null ? !weight.equals(parameter.weight) : parameter.weight != null) return false;
        if (Categories != null ? !Categories.equals(parameter.Categories) : parameter.Categories != null) return false;
        return agentPosition != null ? agentPosition.equals(parameter.agentPosition) : parameter.agentPosition == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (bulkStatus ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (Categories != null ? Categories.hashCode() : 0);
        result = 31 * result + (agentPosition != null ? agentPosition.hashCode() : 0);
        return result;
    }
}
