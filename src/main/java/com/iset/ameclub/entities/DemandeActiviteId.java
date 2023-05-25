package com.iset.ameclub.entities;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

    @Embeddable
    public class DemandeActiviteId implements Serializable {
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id", referencedColumnName = "userId")
        private User user;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "activite_id", referencedColumnName = "activiteId")
        private Activite activite;

        public DemandeActiviteId() {
        }

        public DemandeActiviteId(User user, Activite activite) {
            this.user = user;
            this.activite = activite;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Activite getActivite() {
            return activite;
        }

        public void setActivite(Activite activite) {
            this.activite = activite;
        }
    }

