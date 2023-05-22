package com.iset.ameclub.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "club", uniqueConstraints = @UniqueConstraint(columnNames = "nomClub"))
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;
    private String nomClub;
    private Integer nbMembre;
    private String demandeStatus="aucune demande";

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;
    @ManyToMany(mappedBy = "clubs")
    private Set<User> users = new HashSet<>();
    @ManyToOne
    private User president;
    @OneToMany(mappedBy = "club")
    private List<Activite> activite;

    public Club(String nomClub, Integer nbMembre, Date dateCreation, Set<User> users, User president, List<Activite> activite) {
        this.nomClub = nomClub;
        this.nbMembre = nbMembre;
        this.dateCreation = dateCreation;
        this.users = users;
        this.president = president;
        this.activite=activite;
    }

    public Club() {
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getPresident() {
        return president;
    }

    public void setPresident(User president) {
        this.president = president;
    }

    public String getDemandeStatus() {
        return demandeStatus;
    }

    public void setDemandeStatus(String demandeStatus) {
        this.demandeStatus = demandeStatus;
    }

    public List<Activite> getActivite() {
        return activite;
    }

    public void setActivite(List<Activite> activite) {
        this.activite = activite;
    }
}
