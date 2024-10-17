package com.damian.figinski.dentistapp.service;

import com.damian.figinski.dentistapp.model.Patient;
import com.damian.figinski.dentistapp.repository.PatientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientDetailsService implements UserDetailsService {

    private final PatientRepository patientRepository;

    public PatientDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found with username: " + username));

        return User.builder()
                .username(patient.getUsername())
                .password(patient.getPassword()) // Upewnij się, że hasło jest hashowane
                .roles("PATIENT")
                .build();
    }
}
