package com.blibli.future.detroit.model.dto;

public class BatchUpdateParameterDto {
    Long parameterId;
    Float weight;

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
