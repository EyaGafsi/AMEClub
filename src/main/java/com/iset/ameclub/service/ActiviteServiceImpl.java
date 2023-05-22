package com.iset.ameclub.service;

import com.iset.ameclub.dao.ActiviteRepository;
import com.iset.ameclub.dao.ClubRepository;
import com.iset.ameclub.entities.Activite;
import com.iset.ameclub.entities.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActiviteServiceImpl implements ActiviteService{
    @Autowired
    ActiviteRepository activiteRepository;
    @Override
    public Page<Activite> findByNomActivite(String nom, Pageable pageable) {
        return activiteRepository.findByNomActivite(nom, pageable);
    }
    @Override
    public Page<Activite> getAllActiviteParPage(String nom, int page, int size) {
        Page<Activite> activite;
        if(nom.isEmpty()) {
            activite = activiteRepository.findAll(PageRequest.of(page, size));
        } else {
            activite = activiteRepository.findByNomActivite(nom,PageRequest.of(page, size));
        }return activite;
    }
    @Override
    public Activite getActivite(Long id) {
        return activiteRepository.findById(id).get();
    }
    @Override
    public Activite save(Activite c){
        return activiteRepository.save(c);
    }
    @Override
    public void deleteActivite(Activite a) {
        activiteRepository.delete(a);

    }
    @Override
    public void deleteActiviteById(Long id) {
        activiteRepository.deleteById(id); }
    @Override
    public Activite updateActivite(Activite m) {
        return activiteRepository.save(m);
    }
}
