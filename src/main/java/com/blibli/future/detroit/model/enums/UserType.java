package com.blibli.future.detroit.model.enums;

public enum UserType {
    AGENT("Agent"),
    REVIEWER("Reviewer"),
    SUPER_ADMIN("SuperAdmin");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
