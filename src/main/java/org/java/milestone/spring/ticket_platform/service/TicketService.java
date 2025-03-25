package org.java.milestone.spring.ticket_platform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.java.milestone.spring.ticket_platform.model.Nota;
import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.java.milestone.spring.ticket_platform.repository.NotaRepository;
import org.java.milestone.spring.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NotaRepository notaRepository;

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Integer id){
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isEmpty()) {
            throw new IllegalArgumentException("Ticket non trovato: " + id);

        }
        return ticket.get();
    }

    public Ticket createTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public Ticket editTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id){
        ticketRepository.deleteById(id);
    }

    public List<Ticket> searchTicketByTitle(String titolo){
        return ticketRepository.findByTitoloContaining(titolo);
    }

    public void addNota(Integer id, String contenuto){
        Ticket ticket = getTicketById(id);
        Nota nota = new Nota();
        nota.setContenuto(contenuto);
        nota.setDataCreazione(LocalDateTime.now());
        nota.setTicket(ticket);

        notaRepository.save(nota);
    }
}
