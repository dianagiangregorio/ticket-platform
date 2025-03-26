package org.java.milestone.spring.ticket_platform.controller;

import java.util.List;

import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.java.milestone.spring.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {
    
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Ticket> getTicketByCategoria(@PathVariable String categoria){
        return ticketRepository.findByCategoria(categoria);
    }

    @GetMapping("/stato/{stato}")
    public List<Ticket> getTicketByStato(@PathVariable String stato){
        return ticketRepository.findByStato(stato);
    }

    @GetMapping("/categoria/{categoria}/stato/{stato}")
    public List<Ticket> getTicketByCategoriaAndStato(@PathVariable String categoria, @PathVariable String stato){
        return ticketRepository.getTicketByCategoriaAndStato(categoria, stato);
    }
}
