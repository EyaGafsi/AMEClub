package com.iset.ameclub.dao;

import com.iset.ameclub.entities.Club;
import com.iset.ameclub.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Page<Club> findByNomClub(String nom, Pageable pageable);


    Page<Club> findByPresident(User president, Pageable pageable);
}