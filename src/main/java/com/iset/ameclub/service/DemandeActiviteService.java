package com.iset.ameclub.service;

import com.iset.ameclub.entities.DemandeActivite;
import com.iset.ameclub.entities.DemandeActiviteId;
import com.iset.ameclub.entities.DemandeAjoutActivite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DemandeActiviteService {
    DemandeActivite saveDemandeActivite(DemandeActivite d);
    Page<DemandeActivite> getAllDemandeActiviteParPage(String nom, int page, int size);
    DemandeActivite getDemandeActivite(DemandeActiviteId id);

     String getDemandeStatut(Long userId, Long activiteId);

}
