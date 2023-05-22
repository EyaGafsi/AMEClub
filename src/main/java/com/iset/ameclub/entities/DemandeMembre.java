package com.iset.ameclub.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "demandeMembre")
public class DemandeMembre {
    @EmbeddedId
        private DemandeMembreId id;

        @Column(name = "status")
        private String status;
        private LocalDate dateEnvoi;
        public DemandeMembre(DemandeMembreId id, String status) {
        this.id = id;
        this.status = status;
        this.dateEnvoi=LocalDate.now();
    }

    public DemandeMembre() {
    }

    public DemandeMembreId getId() {
        return id;
    }

    public void setId(DemandeMembreId id) {
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
}

