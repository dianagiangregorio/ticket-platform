package org.java.milestone.spring.ticket_platform.controller;

import java.util.List;
import java.util.Optional;

import org.java.milestone.spring.ticket_platform.model.Categoria;
import org.java.milestone.spring.ticket_platform.model.Operatore;
import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.java.milestone.spring.ticket_platform.repository.CategoriaRepository;
import org.java.milestone.spring.ticket_platform.repository.OperatoreRepository;
import org.java.milestone.spring.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OperatoreRepository operatoreRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String index(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket non trovato");
        }
        model.addAttribute("ticket", ticket.get());
        return "tickets/show";
    }

    @GetMapping("/search")
    public String findByKeyword(@RequestParam(name = "query") String query, Model model) {
        List<Ticket> tickets = ticketRepository.findByTitoloContaining(query);
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("/create-or-edit")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("create", true);
        model.addAttribute("operatori", operatoreRepository.findAll());
        model.addAttribute("categorie", categoriaRepository.findAll());
        return "tickets/create-or-edit";
    }

    @PostMapping("/create-or-edit")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket,
            @RequestParam("operatoreId") Integer operatoreId, @RequestParam("categoriaId") Integer categoriaId,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("operatori", operatoreRepository.findAll());
            model.addAttribute("categorie", categoriaRepository.findAll());
            return "tickets/create-or-edit";
        }
        Operatore operatore = operatoreRepository.findById(operatoreId).get();
        Categoria categoria = categoriaRepository.findById(categoriaId).get();

        formTicket.setOperatore(operatore);
        formTicket.setCategoria(categoria);

        if (formTicket.getCategoria() == null) {
            throw new IllegalArgumentException("La categoria non può essere vuota");
        }

        ticketRepository.save(formTicket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        model.addAttribute("create", false);
        model.addAttribute("operatori", operatoreRepository.findAll());
        model.addAttribute("categorie", categoriaRepository.findAll());
        return "tickets/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, @Valid @ModelAttribute("ticket") Ticket formTicket, @RequestParam("operatoreId") Integer operatoreId, @RequestParam("categoriaId") Integer categoriaId, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("operatori", operatoreRepository.findAll());
            model.addAttribute("categorie", categoriaRepository.findAll());
            return "tickets/create-or-edit";
        }
        Ticket editTicket = ticketRepository.findById(id).get();
        Operatore operatore = operatoreRepository.findById(operatoreId).get();
        Categoria categoria = categoriaRepository.findById(categoriaId).get();

        editTicket.setOperatore(operatore);
        editTicket.setCategoria(categoria);
        editTicket.setTitolo(formTicket.getTitolo());
        editTicket.setContenuto(formTicket.getContenuto());
        editTicket.setStato(formTicket.getStato());;

        ticketRepository.save(editTicket);
        return "redirect:/tickets";
    }
}
