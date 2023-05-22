package com.iset.ameclub.service;

import com.iset.ameclub.dao.DemandeAjoutActiviteRepository;
import com.iset.ameclub.dao.DemandeClubRepository;
import com.iset.ameclub.entities.DemandeActivite;
import com.iset.ameclub.entities.DemandeAjoutActivite;
import com.iset.ameclub.entities.DemandeClub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeAjoutActiviteServiceImpl implements DemandeAjoutActiviteService{
    @Autowired
    DemandeAjoutActiviteRepository demandeAjoutActiviteRepository;
    @Override
    public DemandeAjoutActivite saveDemandeAjoutActivite(DemandeAjoutActivite d){return demandeAjoutActiviteRepository.save(d);
    }

    @Override
    public Page<DemandeAjoutActivite> getAllDemandeAjoutActiviteParPage(String nom, int page, int size) {
        Page<DemandeAjoutActivite> demandeAjoutActivite;
            demandeAjoutActivite = demandeAjoutActiviteRepository.findAll(PageRequest.of(page, size));
        return demandeAjoutActivite;
    }
    @Override
    public DemandeAjoutActivite getDemandeAjoutActivite(Long id) {
        return demandeAjoutActiviteRepository.findById(id).get();
    }
}
