package com.iset.ameclub.dao;


import com.iset.ameclub.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String nom);
}
