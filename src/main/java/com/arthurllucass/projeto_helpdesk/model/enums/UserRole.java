package com.arthurllucass.projeto_helpdesk.model.enums;

public enum UserRole {
    USER("user"),
    TECHNICAL("technical"),
    ADMIN("admin");

    private String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
