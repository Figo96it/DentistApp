package com.damian.figinski.dentistapp.service;

import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.model.User;
import com.damian.figinski.dentistapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistService {

    private final UserRepository userRepository;

    public DentistService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Pobieranie wszystkich dentystów
    public List<User> findAllDentists() {
        return userRepository.findAllByRole(Role.DENTIST);
    }

    //Pobieranie dentysty po ID
    public User findDentistById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //Dodawanie lub aktualizowanie dentysty
    public void saveDentist(User dentist) {
        userRepository.save(dentist);
    }

    //Usuwanie dentysty
    public void deleteDentist(Long id) {
        userRepository.deleteById(id);
    }
}
