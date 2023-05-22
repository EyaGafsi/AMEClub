package com.iset.ameclub.service;


import com.iset.ameclub.entities.DemandeMembre;
import com.iset.ameclub.entities.DemandeMembreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemandeMembreService {
    DemandeMembre saveDemandeMembre(DemandeMembre d);
    Page<DemandeMembre> getAllDemandeMembreParPage(String nom, int page, int size);
    DemandeMembre getDemandeMembre(DemandeMembreId id);
    String getDemandeStatut(Long userId, Long activiteId);
}
