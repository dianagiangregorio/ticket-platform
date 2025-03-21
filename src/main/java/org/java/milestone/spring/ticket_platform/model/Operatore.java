package org.java.milestone.spring.ticket_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "operatori")
public class Operatore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank (message = "Il nome non può essere vuoto")
    private String nome;

    @NotBlank (message = "Il cognome non può essere vuoto")
    private String cognome;

    @NotBlank (message = "l'email non può essere un campo vuoto")
    private String email;

    @NotBlank (message = "la password non può essere vuota")
    private String password;  

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return String.format("nome: %s, cognome: %s, id: %s", this.nome, this.cognome, this.id);
    }
}
