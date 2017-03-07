package com.blibli.future.detroit.model.response;

import java.util.List;

public class BaseRestListResponse<T> {
    private boolean success;
    private String errorMessage;
    private int errorCode;
    private List<T> content;

    public BaseRestListResponse(List<T> content) {
        this.success = true;
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
