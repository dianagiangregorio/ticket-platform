package org.java.milestone.spring.ticket_platform.repository;

import org.java.milestone.spring.ticket_platform.model.Operatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatoreRepository extends JpaRepository<Operatore, Integer> {

    
}
