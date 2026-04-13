package com.arthurllucass.projeto_helpdesk.controller;

import com.arthurllucass.projeto_helpdesk.dto.CreateTicketDTO;
import com.arthurllucass.projeto_helpdesk.dto.TicketDTO;
import com.arthurllucass.projeto_helpdesk.model.entites.Ticket;
import com.arthurllucass.projeto_helpdesk.model.enums.Priority;
import com.arthurllucass.projeto_helpdesk.model.enums.StatusTicket;
import com.arthurllucass.projeto_helpdesk.service.TicketService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> findAll() {
        List<Ticket> listTicket = ticketService.findAll();
        List<TicketDTO> listTicketDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listTicketDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicketDTO> findById(@PathVariable Long id) {
        Ticket ticket = ticketService.findById(id);
        return ResponseEntity.ok().body(new TicketDTO(ticket));
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<List<TicketDTO>> findByTitle(@PathVariable String title) {
        List<Ticket> listTicket = ticketService.findByTitle(title);
        List<TicketDTO> listDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/openingDate")
    public ResponseEntity<List<TicketDTO>> findByOpeningDate(@RequestParam String date) {
        LocalDateTime openingDate = LocalDateTime.parse(date);

        List<Ticket> listTicket = ticketService.findByOpeningDate(openingDate);
        List<TicketDTO> listDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/status/{statusTicket}")
    public ResponseEntity<List<TicketDTO>> findByStatusTicket(@PathVariable StatusTicket statusTicket) {
        List<Ticket> listTicket = ticketService.findByStatusTicket(statusTicket);
        List<TicketDTO> listDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/priority/{priorityTicket}")
    public ResponseEntity<List<TicketDTO>> findByPriorityTicket(@PathVariable Priority priorityTicket) {
        List<Ticket> listTicket = ticketService.findByPriorityTicket(priorityTicket);
        List<TicketDTO> listDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/requestBy/{id}")
    public ResponseEntity<List<TicketDTO>> findByRequestBy(@PathVariable Long id) {
        List<Ticket> listTicket = ticketService.findByRequesterId(id);
        List<TicketDTO> listDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/assignedTo/{id}")
    public ResponseEntity<List<TicketDTO>> findByAssignedTo(@PathVariable Long id){
        List<Ticket> listTicket = ticketService.findAssignedToById(id);
        List<TicketDTO> listDTO = listTicket.stream().map(t -> new TicketDTO(t)).toList();
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CreateTicketDTO createTicketDTO) {
        Ticket ticket = ticketService.fromCreateTicket(createTicketDTO);
        ticketService.insert(ticket);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticket.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CreateTicketDTO createTicketDTO, @PathVariable Long id) {
        Ticket ticket = ticketService.fromCreateTicket(createTicketDTO);
        ticket.setId(id);
        ticketService.update(ticket);
        return ResponseEntity.noContent().build();
    }
}