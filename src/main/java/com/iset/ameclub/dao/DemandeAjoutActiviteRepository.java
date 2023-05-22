package com.iset.ameclub.dao;

import com.iset.ameclub.entities.DemandeActivite;
import com.iset.ameclub.entities.DemandeAjoutActivite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeAjoutActiviteRepository extends JpaRepository<DemandeAjoutActivite, Long> {

}
