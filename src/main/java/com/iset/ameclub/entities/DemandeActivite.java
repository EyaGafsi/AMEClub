package com.iset.ameclub.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "demandeActivite")
public class DemandeActivite {
    @EmbeddedId
    private DemandeActiviteId id;

    @Column(name = "status")
    private String status;
    private LocalDate dateEnvoi;

    public DemandeActivite(DemandeActiviteId id, String status) {
        this.id = id;
        this.status = status;
        this.dateEnvoi = LocalDate.now();
    }

    public DemandeActivite() {
    }

    public DemandeActiviteId getId() {
        return id;
    }

    public void setId(DemandeActiviteId id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

}