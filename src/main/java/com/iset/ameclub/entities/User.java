package com.iset.ameclub.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private String tel;
    @NotNull
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "membres", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id"))
    private Set<Club> clubs = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "president")
    private List<Club> club;
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Activite> activite = new HashSet<>();
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    @Override
    public boolean isEnabled() {
        return false;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, String email, String nom, String prenom, String tel, String password, Set<Role> roles, Set<Club> clubs, List<Club> club) {
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.password = password;
        this.roles = roles;
        this.clubs = clubs;
        this.club = club;
    }

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        } return authorities;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Set<Club> getClubs() {
        return clubs;
    }

    public void setClubs(Set<Club> clubs) {
        this.clubs = clubs;
    }

    public List<Club> getClub() {
        return club;
    }

    public void setClub(List<Club> club) {
        this.club = club;
    }

    public Set<Activite> getActivite() {
        return activite;
    }

    public void setActivite(Set<Activite> activite) {
        this.activite = activite;
    }
    public void setId(Long id) {
        this.userId = id;
    }
    public Long getId() {
        return this.userId ;
    }

}