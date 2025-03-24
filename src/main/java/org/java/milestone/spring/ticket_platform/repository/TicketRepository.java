package org.java.milestone.spring.ticket_platform.repository;

import java.util.List;

import org.java.milestone.spring.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByTitoloContaining(String titolo);
    
}
