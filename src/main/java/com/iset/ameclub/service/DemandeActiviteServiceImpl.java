package com.iset.ameclub.service;

import com.iset.ameclub.dao.DemandeActiviteRepository;
import com.iset.ameclub.dao.DemandeClubRepository;
import com.iset.ameclub.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeActiviteServiceImpl implements DemandeActiviteService {
    @Autowired
    DemandeActiviteRepository demandeActiviteRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ActiviteService activiteService;

    @Override
    public DemandeActivite saveDemandeActivite(DemandeActivite d) {
        return demandeActiviteRepository.save(d);
    }

    @Override
    public Page<DemandeActivite> getAllDemandeActiviteParPage(String nom, int page, int size) {
        Page<DemandeActivite> demandeActivite;

        demandeActivite = demandeActiviteRepository.findAll(PageRequest.of(page, size));
        return demandeActivite;
    }

    @Override
    public DemandeActivite getDemandeActivite(DemandeActiviteId id) {
        return demandeActiviteRepository.findById(id);
    }

@Override
    public String getDemandeStatut(Long userId, Long activiteId) {
        User user = userService.userRepository.getById(userId);
        Activite activite = activiteService.getActivite(activiteId);
        DemandeActiviteId demandeActiviteId = new DemandeActiviteId(user, activite);
        DemandeActivite demandeActivite = getDemandeActivite(demandeActiviteId);
    if (demandeActivite!=null) {
        return demandeActivite.getStatus();
    } else {
        return "aucune demande";
    }
    }
}