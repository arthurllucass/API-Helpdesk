package com.arthurllucass.projeto_helpdesk.service;

import com.arthurllucass.projeto_helpdesk.dto.CreateTicketDTO;
import com.arthurllucass.projeto_helpdesk.exceptions.InvalidDataException;
import com.arthurllucass.projeto_helpdesk.exceptions.ObjectNotFoundException;
import com.arthurllucass.projeto_helpdesk.model.entites.Ticket;
import com.arthurllucass.projeto_helpdesk.model.entites.User;
import com.arthurllucass.projeto_helpdesk.model.enums.Priority;
import com.arthurllucass.projeto_helpdesk.model.enums.StatusTicket;
import com.arthurllucass.projeto_helpdesk.model.enums.UserRole;
import com.arthurllucass.projeto_helpdesk.repository.TicketRepository;
import com.arthurllucass.projeto_helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Ticket> findAll() {
        List<Ticket> listTickets = ticketRepository.findAll();
        return listTickets;
    }

    public Ticket findById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.
                orElseThrow(() -> new ObjectNotFoundException("Id não encontrado"));
    }

    public List<Ticket> findByTitle(String title) {
        Optional<List<Ticket>> ticketsTitle = ticketRepository.findByTitleIgnoreCase(title);
        return ticketsTitle
                .orElseThrow(() -> new ObjectNotFoundException("Chamado de título " + title + " não foi encontrado"));
    }

    public List<Ticket> findByOpeningDate(LocalDateTime openingDate) {
        Optional<List<Ticket>> ticketsOpeningDate = ticketRepository.findByOpeningDate(openingDate);
        return ticketsOpeningDate
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum chamado com a data de abertura " + openingDate + " foi encontrado!"));
    }

    public List<Ticket> findByStatusTicket(StatusTicket statusTicket) {
        Optional<List<Ticket>> ticketsStatusTicket = ticketRepository.findByStatusTicket(statusTicket);
        return ticketsStatusTicket
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum chamado com status " + statusTicket + " foi encontrado"));
    }

    public List<Ticket> findByPriorityTicket(Priority priorityTicket) {
        Optional<List<Ticket>> ticketsPriorityTicket = ticketRepository.findByPriorityTicket(priorityTicket);
        return ticketsPriorityTicket
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum chamado com prioridade " + priorityTicket + " foi encontrado"));
    }

    public List<Ticket> findByRequesterId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Id não encontrado"));

        Optional<List<Ticket>> ticketsRequestBy = ticketRepository.findByRequestBy(user);
        return ticketsRequestBy
                .orElseThrow(() -> new ObjectNotFoundException("Nenhum chamado solicitado por  " + user.getName() + " foi encontrado"));
    }

    public List<Ticket> findAssignedToById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Id não encontrado"));

        Optional<List<Ticket>> ticketsAssignedTo = ticketRepository.findByAssignedTo(user);
        return ticketsAssignedTo.
                orElseThrow(() -> new ObjectNotFoundException("Nenhum chamado solicitado por " + user.getName() + " foi encontrado"));
    }

    public Ticket insert(Ticket ticket) {
        validateTicket(ticket);
        validateAssignedTo(ticket);
        if (ticket.getStatusTicket() == null || ticket.getStatusTicket() == StatusTicket.CLOSED) ticket.setStatusTicket(StatusTicket.OPEN);
        if (ticket.getPriorityTicket() == null) ticket.setPriorityTicket(Priority.LOW);
        return ticketRepository.save(ticket);
    }

    public void delete(Long id) {
        Ticket ticket = findById(id);
        ticketRepository.delete(ticket);
    }

    public Ticket update(Ticket ticket) {
        Ticket oldTicket = findById(ticket.getId());
        validateTicket(ticket);
        validateAssignedTo(ticket);
        validateClosedTicket(oldTicket);
        validateStatusTransition(oldTicket, ticket);
        validateCloseWithoutTechnician(ticket);
        updateData(ticket, oldTicket);
        return ticketRepository.save(oldTicket);
    }

    public Ticket updateData(Ticket ticket, Ticket newTicket) {
        newTicket.setTitle(ticket.getTitle());
        newTicket.setDescription(ticket.getDescription());
        newTicket.setStatusTicket(ticket.getStatusTicket());
        newTicket.setPriorityTicket(ticket.getPriorityTicket());
        newTicket.setAssignedTo(ticket.getAssignedTo());
        return newTicket;
    }

    public Ticket fromCreateTicket(CreateTicketDTO dto) {
        User requestBy = userRepository.findById(dto.requestById())
                .orElseThrow(() -> new ObjectNotFoundException("Solicitante não encontrado."));

        User assignedTo = null;
        if (dto.assignedToId() != null) assignedTo = userRepository.findById(dto.assignedToId())
                    .orElseThrow(() -> new ObjectNotFoundException("Usuário atribuído não encontrado."));

        return new Ticket(
                null,
                dto.title(),
                dto.description(),
                null,
                dto.statusTicket(),
                dto.priorityTicket(),
                requestBy,
                assignedTo
        );
    }

    private void validateTicket(Ticket ticket) {
        if (ticket.getRequestBy() == null) throw new InvalidDataException("Chamado precisa de um solicitante.");
        userRepository.findById(ticket.getRequestBy().getId())
                .orElseThrow(() -> new ObjectNotFoundException("Solicitante não encontrado."));
    }

    private void validateAssignedTo(Ticket ticket) {
        if (ticket.getAssignedTo() != null) {
            User user = userRepository.findById(ticket.getAssignedTo().getId())
                    .orElseThrow(() -> new ObjectNotFoundException("Usuário atribuído não encontrado."));

            if (user.getRole() != UserRole.TECHNICAL) throw new InvalidDataException("Somente técnicos podem ser atribuídos a chamados.");
        }
    }

    private void validateStatusTransition(Ticket oldTicket, Ticket newTicket) {
        if (oldTicket.getStatusTicket() == StatusTicket.CLOSED && newTicket.getStatusTicket() == StatusTicket.OPEN)
            throw new InvalidDataException("Chamado fechado não pode ser reaberto.");
    }

    private void validateClosedTicket(Ticket ticket) {
        if (ticket.getStatusTicket() == StatusTicket.CLOSED) throw new InvalidDataException("Chamado fechado não pode ser alterado.");
    }

    private void validateCloseWithoutTechnician(Ticket ticket) {
        if (ticket.getStatusTicket() == StatusTicket.CLOSED && ticket.getAssignedTo() == null)
            throw new InvalidDataException("Chamado não pode ser fechado sem técnico atribuído.");
    }
}
