package com.blibli.future.detroit.model.request;

public class NewCategoryRequest {
    private Long id;
    private String name;
    private String description;
    private boolean bulkStatus;
    private boolean isActive;

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

    @Override
    public String toString() {
        return "NewCategoryRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bulkStatus=" + bulkStatus +
                ", isActive=" + isActive +
                '}';
    }
}
