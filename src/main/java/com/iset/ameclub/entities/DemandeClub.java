package com.iset.ameclub.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "DemandeClub", uniqueConstraints = @UniqueConstraint(columnNames = "nomClub"))
public class DemandeClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandeId;
    private String nomClub;
    private Integer nbMembre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;
    @ManyToOne
    private User user;
    private LocalDate dateEnvoi;
    private String status;
    public DemandeClub(String nomClub, Integer nbMembre, Date dateCreation, User user,String status) {
        this.nomClub = nomClub;
        this.nbMembre = nbMembre;
        this.dateCreation = dateCreation;
        this.user = user;
        this.status=status;
        this.dateEnvoi=LocalDate.now();
    }

    public DemandeClub() {
    }


    public String getNomClub() {
        return nomClub;
    }

    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

    public Integer getNbMembre() {
        return nbMembre;
    }

    public void setNbMembre(Integer nbMembre) {
        this.nbMembre = nbMembre;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(Long demandeId) {
        this.demandeId = demandeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
