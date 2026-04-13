package com.arthurllucass.projeto_helpdesk.model.entites;

import com.arthurllucass.projeto_helpdesk.model.enums.Priority;
import com.arthurllucass.projeto_helpdesk.model.enums.StatusTicket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título não pode ser nulo ou vazio")
    private String title;

    @NotBlank(message = "A descrição não pode ser nula ou vazia")
    private String description;

    @CreationTimestamp
    private LocalDateTime openingDate;

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @Enumerated(EnumType.STRING)
    private Priority priorityTicket;

    @ManyToOne
    private User requestBy;

    @ManyToOne
    private User assignedTo;

    public Ticket() {
    }

    public Ticket(Long id, String title, String description, LocalDateTime openingDate, StatusTicket statusTicket, Priority priorityTicket, User requestBy, User assignedTo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.openingDate = openingDate;
        this.statusTicket = statusTicket;
        this.priorityTicket = priorityTicket;
        this.requestBy = requestBy;
        this.assignedTo = assignedTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public StatusTicket getStatusTicket() {
        return statusTicket;
    }

    public void setStatusTicket(StatusTicket statusTicket) {
        this.statusTicket = statusTicket;
    }

    public Priority getPriorityTicket() {
        return priorityTicket;
    }

    public void setPriorityTicket(Priority priorityTicket) {
        this.priorityTicket = priorityTicket;
    }

    public User getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(User requestBy) {
        this.requestBy = requestBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
