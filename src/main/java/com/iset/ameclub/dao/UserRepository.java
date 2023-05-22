package com.iset.ameclub.dao;

import com.iset.ameclub.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = ?1")
    Optional<User> findUserWithName(String username);

    @Query("select u from User u where u.username = ?1")
    User findUsername(String username);



}