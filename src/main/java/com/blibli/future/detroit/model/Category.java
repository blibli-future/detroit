package com.blibli.future.detroit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ManyToOne
    private AgentChannel agentChannel;

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

    @JsonIgnore
    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public AgentChannel getAgentChannel() {
        return agentChannel;
    }

    public void setAgentChannel(AgentChannel agentChannel) {
        this.agentChannel = agentChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;
        if (weight != null ? !weight.equals(category.weight) : category.weight != null) return false;
        if (parameter != null ? !parameter.equals(category.parameter) : category.parameter != null) return false;
        return agentChannel != null ? agentChannel.equals(category.agentChannel) : category.agentChannel == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + (agentChannel != null ? agentChannel.hashCode() : 0);
        return result;
    }
}
