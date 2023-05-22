package com.iset.ameclub.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "DemandeAjoutActivit", uniqueConstraints = @UniqueConstraint(columnNames = "nomActivite"))
public class DemandeAjoutActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandeActiviteId;
    private String nomActivite;
    private String lieu;
    private Date dateActivite;
    private float prixActivite;
    private String sujet;
    private String status;
    @ManyToOne
    private Club club;
    private LocalDate dateEnvoi;
    public DemandeAjoutActivite(String nomActivite, String lieu, Date dateActivite, float prixActivite, String sujet, Club club,String status) {
        this.nomActivite = nomActivite;
        this.lieu = lieu;
        this.dateActivite = dateActivite;
        this.prixActivite = prixActivite;
        this.sujet = sujet;
        this.club = club;
        this.status=status;
        this.dateEnvoi = LocalDate.now();
    }

    public DemandeAjoutActivite() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDemandeActiviteId() {
        return demandeActiviteId;
    }

    public void setDemandeActiviteId(Long demandeActiviteId) {
        this.demandeActiviteId = demandeActiviteId;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateActivite() {
        return dateActivite;
    }

    public void setDateActivite(Date dateActivite) {
        this.dateActivite = dateActivite;
    }

    public float getPrixActivite() {
        return prixActivite;
    }

    public void setPrixActivite(float prixActivite) {
        this.prixActivite = prixActivite;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDate dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

}
