package com.iset.ameclub.dao;

import com.iset.ameclub.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandeActiviteRepository extends JpaRepository<DemandeActivite, Long> {
    DemandeActivite findById(DemandeActiviteId id);
}
