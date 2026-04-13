package com.arthurllucass.projeto_helpdesk.repository;

import com.arthurllucass.projeto_helpdesk.model.entites.Ticket;
import com.arthurllucass.projeto_helpdesk.model.entites.User;
import com.arthurllucass.projeto_helpdesk.model.enums.Priority;
import com.arthurllucass.projeto_helpdesk.model.enums.StatusTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

     Optional<List<Ticket>> findByTitleIgnoreCase(String title);
     Optional<List<Ticket>> findByOpeningDate(LocalDateTime openingDate);
     Optional<List<Ticket>> findByStatusTicket (StatusTicket statusTicket);
     Optional<List<Ticket>> findByPriorityTicket(Priority priorityTicket);
     Optional<List<Ticket>> findByRequestBy(User requestBy);
     Optional<List<Ticket>> findByAssignedTo(User assignedTo);
}
