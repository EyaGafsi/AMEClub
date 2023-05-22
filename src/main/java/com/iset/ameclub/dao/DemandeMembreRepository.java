package com.iset.ameclub.dao;

import com.iset.ameclub.entities.DemandeMembre;
import com.iset.ameclub.entities.DemandeMembreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandeMembreRepository extends JpaRepository<DemandeMembre, Long> {
    DemandeMembre findById(DemandeMembreId id);

}
