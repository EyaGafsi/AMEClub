package com.iset.ameclub.service;

import com.iset.ameclub.dao.DemandeMembreRepository;
import com.iset.ameclub.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeMembreServiceImpl implements DemandeMembreService{
    @Autowired
    DemandeMembreRepository demandeMembreRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ClubService clubService;
    @Override
    public DemandeMembre saveDemandeMembre(DemandeMembre d){
        return demandeMembreRepository.save(d);
    }
    @Override
    public Page<DemandeMembre> getAllDemandeMembreParPage(String nom, int page, int size) {
        Page<DemandeMembre> demandeMembre;

            demandeMembre = demandeMembreRepository.findAll(PageRequest.of(page, size));
     return demandeMembre;
    }
    @Override
    public DemandeMembre getDemandeMembre(DemandeMembreId id) {
        return demandeMembreRepository.findById(id);
    }
    @Override
    public String getDemandeStatut(Long userId, Long clubId) {
        User user = userService.userRepository.getById(userId);
        Club club = clubService.getClub(clubId);
        DemandeMembreId demandeMembreId = new DemandeMembreId(user, club);
        DemandeMembre demandeMembre = getDemandeMembre(demandeMembreId);
        if (demandeMembre!=null) {
            return demandeMembre.getStatus();
        } else {
            return "aucune demande";
        }
    }
}
