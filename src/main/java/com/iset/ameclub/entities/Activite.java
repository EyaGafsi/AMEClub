package com.iset.ameclub.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Activite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activiteId;
    private String nomActivite;
    private String lieu;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateActivite;
    private float prixActivite;
    private String sujet;
    private String demandeStatus="aucune demande";
    @ManyToOne(cascade = CascadeType.ALL)
    private Club club;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_activite", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activite_id"))
    private Set<User> user = new HashSet<>();

    public Activite(String nomActivite, String lieu, Date dateActivite, float prixActivite, String sujet, Club club, Set<User> user) {
        this.nomActivite = nomActivite;
        this.lieu = lieu;
        this.dateActivite = dateActivite;
        this.prixActivite = prixActivite;
        this.sujet = sujet;
        this.club = club;
        this.user = user;
    }

    public Activite() {
    }

    public Long getActiviteId() {
        return activiteId;
    }

    public void setActiviteId(Long activiteId) {
        this.activiteId = activiteId;
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

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Date getDateActivite() {
        return dateActivite;
    }

    public void setDateActivite(Date dateActivite) {
        this.dateActivite = dateActivite;
    }

    public String getDemandeStatus() {
        return demandeStatus;
    }

    public void setDemandeStatus(String demandeStatus) {
        this.demandeStatus = demandeStatus;
    }
}
