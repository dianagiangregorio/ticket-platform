package org.java.milestone.spring.ticket_platform.controller;

import java.util.List;

import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.java.milestone.spring.ticket_platform.repository.TicketRepository;
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
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    
    @GetMapping
    public String index(Model model){
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }
@GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
    model.addAttribute("ticket", ticketRepository.findById(id).get());
    return "tickets/show";
    }

    @GetMapping("/search")
        public String findByKeyword(@RequestParam(name = "query") String query, Model model){
        List<Ticket> tickets = ticketRepository.findByTitoloContaining(query);
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("/create")
        public String create (Model model){
        model.addAttribute("ticket", new Ticket());
        return "tickets/create";
    }

    @PostMapping("/create")
        public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
        return "tickets/create";
        }
        ticketRepository.save(formTicket);
        return "redirect:/tickets";
    }
}
