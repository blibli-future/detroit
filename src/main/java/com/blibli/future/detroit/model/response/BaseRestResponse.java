package com.blibli.future.detroit.model.response;

public class BaseRestResponse<T> {
    private boolean success;
    private String errorMessage;
    private int errorCode;
    private T content;

    public BaseRestResponse() {
        this.success = true;
    }

    public BaseRestResponse(T content) {
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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
