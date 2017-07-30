package com.blibli.future.detroit.model.enums;

public enum ScoreType {
    ALL_PARAMETER("All Parameter"),
    ALL_CATEGORY("All Category"),
    USER_PARAMETER("User Parameter"),
    USER_CATEGORY("User Category");

    private final String type;

    ScoreType(String type) {
        this.type = type;
    }
}
