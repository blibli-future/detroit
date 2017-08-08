package com.blibli.future.detroit.model.dto;

import java.io.Serializable;

public class UploadModelDto implements Serializable {
    String email;
    String value;

    public UploadModelDto() {
    }

    public UploadModelDto(String email, String value) {
        this.email = email;
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
