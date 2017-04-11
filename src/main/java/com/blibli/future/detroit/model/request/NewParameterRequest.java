package com.blibli.future.detroit.model.request;

import com.blibli.future.detroit.model.Category;

import javax.persistence.ManyToOne;
import java.io.Serializable;

public class NewParameterRequest implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Float weight;

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
}
