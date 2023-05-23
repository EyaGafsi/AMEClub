package com.iset.ameclub.service;

import com.iset.ameclub.entities.Club;
import com.iset.ameclub.entities.DemandeMembre;
import com.iset.ameclub.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubService {
    Page<Club> getAllClubsParPage(String nom, int page, int size);
    Page<Club> findByNomClub(String nom, Pageable pageable);

    List<Club> getAllClubs();
    Club getClub(Long id);
    Club save(Club c);
    Page<Club> getClubsByPresident(User president, int page, int size);

    Club updateClub(Club m);
    void deleteClubById(Long id);
}
