package com.damian.figinski.dentistapp.service;

import com.damian.figinski.dentistapp.model.User;
import com.damian.figinski.dentistapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private  final UserRepository userRepository;

    // Constructor Injection
    public PatientService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
