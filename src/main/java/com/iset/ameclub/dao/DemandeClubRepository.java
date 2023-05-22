package com.iset.ameclub.dao;

import com.iset.ameclub.entities.DemandeAjoutActivite;
import com.iset.ameclub.entities.DemandeClub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeClubRepository extends JpaRepository<DemandeClub, Long> {

}
