package com.iset.ameclub.service;

import com.iset.ameclub.dao.ClubRepository;
import com.iset.ameclub.entities.Activite;
import com.iset.ameclub.entities.Club;
import com.iset.ameclub.entities.DemandeMembre;
import com.iset.ameclub.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService{
    @Autowired
    ClubRepository clubRepository;
    @Override
    public Page<Club> findByNomClub(String nom, Pageable pageable) {
        return clubRepository.findByNomClub(nom, pageable);
    }

    @Override
    public Page<Club> getAllClubsParPage(String nom, int page, int size) {
        Page<Club> club;
        if(nom.isEmpty()) {
            club = clubRepository.findAll(PageRequest.of(page, size));
        } else {
            club = clubRepository.findByNomClub(nom,PageRequest.of(page, size));
        }return club;
    }
    @Override
    public Club getClub(Long id) {
        return clubRepository.findById(id).get();
    }
    @Override
    public List<Club> getAllClubs() {

        return clubRepository.findAll();
    }
    @Override
    public Club save(Club c){
        return clubRepository.save(c);
    }

    @Override
    public Page<Club> getClubsByPresident(User president, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clubRepository.findByPresident(president,pageable);
    }

    @Override
    public Club updateClub(Club m) {
        return clubRepository.save(m);
    }

    @Override
    public void deleteClubById(Long id) {
        clubRepository.deleteById(id); }
}
