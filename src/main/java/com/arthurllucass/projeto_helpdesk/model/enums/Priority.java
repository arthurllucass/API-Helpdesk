package com.arthurllucass.projeto_helpdesk.model.enums;

public enum Priority {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private String descriptionPriority;

    Priority(String descriptionPriority) {
        this.descriptionPriority = descriptionPriority;
    }

    public String getDescriptionPriority() {
        return descriptionPriority;
    }
}
