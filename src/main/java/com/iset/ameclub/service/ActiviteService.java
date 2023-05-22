package com.iset.ameclub.service;

import com.iset.ameclub.entities.Activite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActiviteService {
    Page<Activite> getAllActiviteParPage(String nom, int page, int size);
    Page<Activite> findByNomActivite(String nom, Pageable pageable);
    Activite getActivite(Long id);
    Activite save(Activite c);
    public void deleteActivite(Activite a);
    void deleteActiviteById(Long id);
    Activite updateActivite(Activite m);
}
