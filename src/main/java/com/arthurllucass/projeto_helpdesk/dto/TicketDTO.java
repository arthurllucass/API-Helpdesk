package com.arthurllucass.projeto_helpdesk.dto;

import com.arthurllucass.projeto_helpdesk.model.entites.Ticket;
import com.arthurllucass.projeto_helpdesk.model.entites.User;
import com.arthurllucass.projeto_helpdesk.model.enums.Priority;
import com.arthurllucass.projeto_helpdesk.model.enums.StatusTicket;

import java.io.Serializable;
import java.time.LocalDateTime;

public record TicketDTO(Long id, String title, String description, LocalDateTime openingDate, StatusTicket statusTicket, Priority priorityTicket,
                        User requestBy, User assignedTo) implements Serializable {

    private static final long serialVersionUID = 1L;

    public TicketDTO(Ticket ticket) {
        this(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getOpeningDate(), ticket.getStatusTicket(),
                ticket.getPriorityTicket(), ticket.getRequestBy(), ticket.getAssignedTo());
    }
}
