package com.iset.ameclub.service;

import com.iset.ameclub.dao.DemandeClubRepository;
import com.iset.ameclub.entities.DemandeAjoutActivite;
import com.iset.ameclub.entities.DemandeClub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeClubServiceImpl implements DemandeClubService {
    @Autowired
    DemandeClubRepository demandeClubRepository;
    @Override
    public DemandeClub saveDemandeClub(DemandeClub d){
        return demandeClubRepository.save(d);
    }

    @Override
    public Page<DemandeClub> getAllDemandeClubParPage(String nom, int page, int size) {
        Page<DemandeClub> demandeClub;

            demandeClub = demandeClubRepository.findAll(PageRequest.of(page, size));
        return demandeClub;
    }
    @Override
    public DemandeClub getDemandeClub(Long id) {
        return demandeClubRepository.findById(id).get();
    }
}
