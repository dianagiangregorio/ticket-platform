package org.java.milestone.spring.ticket_platform.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id; 

    @NotBlank (message = "il ticket deve avere un titolo")
    private String titolo;

    @Lob
    @NotBlank (message = "il contenuto non può essere vuoto")
    private String contenuto;

    @NotBlank (message = "lo stato è obbligatorio")
    private String Stato;

    @NotNull (message = "la data è obbligatoria")
    private LocalDateTime dataCreazione;

    private LocalDateTime dataModifica;

    @ManyToOne
    @JoinColumn(name="operatore_id", nullable=false)
    private Operatore operatore;

    @ManyToOne
    @JoinColumn (name = "categoria_id", nullable =false)
    private Categoria categoria;

    @OneToMany(mappedBy = "ticket")
    private List<Nota> note;

    public List<Nota> getNote() {
        return this.note;
    }

    public void setNote(List<Nota> note) {
        this.note = note;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Operatore getOperatore() {
        return this.operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getContenuto() {
        return this.contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getStato() {
        return this.Stato;
    }

    public void setStato(String Stato) {
        this.Stato = Stato;
    }

    public LocalDateTime getDataCreazione() {
        return this.dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public LocalDateTime getDataModifica() {
        return this.dataModifica;
    }

    public void setDataModifica(LocalDateTime dataModifica) {
        this.dataModifica = dataModifica;
    }

    @Override
    public String toString(){
        return String.format("titolo: s%, contenuto: s%", this.titolo, this.contenuto);
    }
}
