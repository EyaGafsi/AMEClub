package com.iset.ameclub.entities;

import javax.persistence.*;
import java.io.Serializable;
@Embeddable
public class DemandeMembreId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id", referencedColumnName = "clubId")
    private Club club;

    public DemandeMembreId() {
    }

    public DemandeMembreId(User user, Club club) {
        this.user = user;
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
