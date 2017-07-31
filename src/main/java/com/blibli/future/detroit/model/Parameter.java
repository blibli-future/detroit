package com.blibli.future.detroit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@SQLDelete(sql =
    "UPDATE parameter " +
    "SET deleted = true " +
    "WHERE id = ?")
@Where(clause = "deleted=false")
public class Parameter extends BaseModel implements Serializable {
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
    @OneToMany
    private List<Review> reviews;

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (bulkStatus != parameter.bulkStatus) return false;
        if (isActive != parameter.isActive) return false;
        if (!id.equals(parameter.id)) return false;
        if (!name.equals(parameter.name)) return false;
        if (!description.equals(parameter.description)) return false;
        if (!weight.equals(parameter.weight)) return false;
        if (Categories != null ? !Categories.equals(parameter.Categories) : parameter.Categories != null) return false;
        if (agentPosition != null ? !agentPosition.equals(parameter.agentPosition) : parameter.agentPosition != null)
            return false;
        return reviews != null ? reviews.equals(parameter.reviews) : parameter.reviews == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + weight.hashCode();
        result = 31 * result + (bulkStatus ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (Categories != null ? Categories.hashCode() : 0);
        result = 31 * result + (agentPosition != null ? agentPosition.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        return result;
    }
}
