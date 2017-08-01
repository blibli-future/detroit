package com.blibli.future.detroit.model.enums;

public enum ScoreType {
    TOTAL_REVIEW("Total Review"),
    ALL_PARAMETER("All Parameter"),
    ALL_CATEGORY("All Category"),
    USER_PARAMETER("User Parameter"),
    USER_CATEGORY("User Category"),
    TOTAL_CHANNEL("Total Channel");

    private final String type;

    ScoreType(String type) {
        this.type = type;
    }
}
