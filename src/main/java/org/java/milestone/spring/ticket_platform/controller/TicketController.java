package org.java.milestone.spring.ticket_platform.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.java.milestone.spring.ticket_platform.model.Categoria;
import org.java.milestone.spring.ticket_platform.model.Operatore;
import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.java.milestone.spring.ticket_platform.repository.CategoriaRepository;
import org.java.milestone.spring.ticket_platform.repository.OperatoreRepository;
import org.java.milestone.spring.ticket_platform.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private OperatoreRepository operatoreRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String index(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket.getNote() == null) {
            ticket.setNote(new ArrayList<>());            
        }
        model.addAttribute("ticket", ticket);
        return "tickets/show";
    }

    @GetMapping("/search")
    public String findByKeyword(@RequestParam(name = "query") String query, Model model) {
        List<Ticket> tickets = ticketService.searchTicketByTitle(query);
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

        ticketService.createTicket(formTicket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
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
        Ticket editTicket = ticketService.getTicketById(id);
        Operatore operatore = operatoreRepository.findById(operatoreId).get();
        Categoria categoria = categoriaRepository.findById(categoriaId).get();

        editTicket.setOperatore(operatore);
        editTicket.setCategoria(categoria);
        editTicket.setTitolo(formTicket.getTitolo());
        editTicket.setContenuto(formTicket.getContenuto());
        editTicket.setStato(formTicket.getStato());;

        ticketService.editTicket(editTicket);
        return "redirect:/tickets";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Ticket ticket = ticketService.getTicketById(id);

        ticketService.deleteTicket(id);
        
        redirectAttributes.addFlashAttribute("message", String.format("%s è stato eliminato", ticket.getTitolo()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");
        return "redirect:/tickets";
    }

    @PostMapping("/add-nota/{ticketId}")
    public String addNota(@PathVariable Integer ticketId, @RequestParam String contenuto, Principal principal){
        ticketService.addNota(ticketId, contenuto, principal);
        return "tickets/create-or-edit";
    }
}
