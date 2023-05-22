package com.iset.ameclub.dao;

import com.iset.ameclub.entities.Activite;
import com.iset.ameclub.entities.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {
    Page<Activite> findByNomActivite(String nom, Pageable pageable);
}
