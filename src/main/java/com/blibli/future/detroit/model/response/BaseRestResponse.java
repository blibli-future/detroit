package com.blibli.future.detroit.model.response;

public class BaseRestResponse<T> {
    private boolean success;
    private String errorMessage;
    private String errorCode;
    private T content;

    public BaseRestResponse() {
        this.success = true;
    }

    public BaseRestResponse(boolean success, String errorMessage, String errorCode) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
