package com.arthurllucass.projeto_helpdesk.model.enums;

public enum StatusTicket {
    OPEN("open"),
    CLOSED("closed");

    private String descriptionTicket;

    StatusTicket(String descriptionTicket) {
        this.descriptionTicket = descriptionTicket;
    }

    public String getDescriptionTicket() {
        return descriptionTicket;
    }
}
