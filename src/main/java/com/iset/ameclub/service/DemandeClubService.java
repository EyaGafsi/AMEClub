package com.iset.ameclub.service;

import com.iset.ameclub.entities.DemandeClub;
import com.iset.ameclub.entities.DemandeMembre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemandeClubService {
    DemandeClub saveDemandeClub(DemandeClub d);
    Page<DemandeClub> getAllDemandeClubParPage(String nom, int page, int size);

    DemandeClub getDemandeClub(Long id);
}
