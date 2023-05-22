package com.iset.ameclub.service;

import com.iset.ameclub.entities.DemandeAjoutActivite;
import com.iset.ameclub.entities.DemandeMembre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemandeAjoutActiviteService {
    DemandeAjoutActivite saveDemandeAjoutActivite(DemandeAjoutActivite d);
    Page<DemandeAjoutActivite> getAllDemandeAjoutActiviteParPage(String nom, int page, int size);

    DemandeAjoutActivite getDemandeAjoutActivite(Long id);
}
