package org.java.milestone.spring.ticket_platform.controller;

import java.security.Principal;
import java.util.List;

import org.java.milestone.spring.ticket_platform.model.Operatore;
import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.java.milestone.spring.ticket_platform.repository.OperatoreRepository;
import org.java.milestone.spring.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/operatore")
public class OperatoreController {
    
    @Autowired
    private TicketService ticketService;

    @Autowired
    private OperatoreRepository operatoreRepository;

    @GetMapping("/tickets")
    public String getTicketsByOperatore(Principal principal, Model model){
        Operatore operatore = operatoreRepository.findByEmail(principal.getName());
        List<Ticket> tickets = ticketService.getTicketsByOperatore(operatore);
        model.addAttribute("tickets", tickets);
        return "operatore/tickets";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Principal principal, Model model){
        Ticket ticket = ticketService.getTicketById(id);
        Operatore operatore = operatoreRepository.findByEmail(principal.getName());

        if (ticket.getOperatore().equals(operatore)) {
            model.addAttribute("tickets", ticket);
            return "operatore/tickets/show";
        }
        return "redirect:/operatore/tickets";
    }

    @PostMapping("/ticket/edit/{id}")
    public String editStato(@PathVariable Integer id, @RequestParam String stato, Principal principal){
        Ticket ticket = ticketService.getTicketById(id);
        Operatore operatore = operatoreRepository.findByEmail(principal.getName());

        if (ticket.getOperatore().equals(operatore)) {
            ticket.setStato(stato);
            ticketService.editTicket(ticket);
            return "redirect:/operatore/tickets" + id;
        }
        return "redirect:/operatore/tickets";
    }

    @PostMapping("/ticket/add-nota/{ticketId}")
    public String addNotaTicket(@PathVariable Integer id, @RequestParam String contenuto, Principal principal){
        Ticket ticket = ticketService.getTicketById(id);
        Operatore operatore = operatoreRepository.findByEmail(principal.getName());

        if (ticket.getOperatore().equals(operatore)) {
            ticketService.addNota(id, contenuto, principal);    
            return "redirect:/operatore/tickets" + id;
    }
    return "redirect:/operatore/tickets";
    }

    @GetMapping("/personal-page")
    public String getPage(Principal principal, Model model){
        Operatore operatore = operatoreRepository.findByEmail(principal.getName());
        model.addAttribute("operatore", operatore);
        return "/operatore/personal-page";
    }

    @PostMapping("/personal-page/edit")
    public String editProfile(@Valid @ModelAttribute Operatore operatore, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "operatore/personal-page";
        }
        Operatore editOperatore = operatoreRepository.findByEmail(principal.getName());
        if (operatore.getStato().equals("non attivo")) {
            boolean hasActiveTickets = ticketService.hasActiveTickets(editOperatore);
            if (hasActiveTickets) {
                return "operatore/personal-page";             
            }
        }
        editOperatore.setStato(operatore.getStato());
        operatoreRepository.save(editOperatore);
        return "redirect:/operatore/personal-page";
    }
}
