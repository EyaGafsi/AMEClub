package com.iset.ameclub.service;

import com.iset.ameclub.dao.UserRepository;
import com.iset.ameclub.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User m) {
        return userRepository.save(m);
    }

    public User updateUser(User m) {
        return userRepository.save(m);
    }

    public void deleteUser(User m) {
        userRepository.delete(m);

    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id); }

    public Page<User> getAllUserParPage(int page, int size) {

        return userRepository.findAll(PageRequest.of(page, size));
    }

}
