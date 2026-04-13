package com.arthurllucass.projeto_helpdesk.dto;

import com.arthurllucass.projeto_helpdesk.model.enums.Priority;
import com.arthurllucass.projeto_helpdesk.model.enums.StatusTicket;

import java.io.Serializable;

public record CreateTicketDTO(String title, String description, StatusTicket statusTicket, Priority priorityTicket,
                              Long requestById, Long assignedToId) implements Serializable {

    private static final long serialVersionUID = 1L;
}
