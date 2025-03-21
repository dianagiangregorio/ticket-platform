package org.java.milestone.spring.ticket_platform.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "note")
public class Nota {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @NotBlank (message = "la nota deve avere un contenuto")
    private String contenuto;

    @NotNull (message = "la data è obbligatoria")
    private LocalDateTime dataCreazione;
}
