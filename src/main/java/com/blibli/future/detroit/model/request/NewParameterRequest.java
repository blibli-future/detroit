package com.blibli.future.detroit.model.request;

public class NewParameterRequest {
    private Long id;
    private String name;
    private String description;
    private Float weight;
    private boolean bulkStatus;
    private boolean isActive;
    private Long agentChannelId;

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

    public Long getAgentChannelId() {
        return agentChannelId;
    }

    public void setAgentChannelId(Long agentChannelId) {
        this.agentChannelId = agentChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewParameterRequest that = (NewParameterRequest) o;

        if (bulkStatus != that.bulkStatus) return false;
        if (isActive != that.isActive) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        return agentChannelId != null ? agentChannelId.equals(that.agentChannelId) : that.agentChannelId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (bulkStatus ? 1 : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (agentChannelId != null ? agentChannelId.hashCode() : 0);
        return result;
    }
}
