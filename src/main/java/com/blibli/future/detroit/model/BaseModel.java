package com.blibli.future.detroit.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel {
    private String uuid;
    private boolean deleted = false;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
