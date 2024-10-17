package com.damian.figinski.dentistapp.config;

import com.damian.figinski.dentistapp.model.Patient;
import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(PatientRepository patientRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (patientRepository.count() == 0) {
                patientRepository.save(new Patient(
                        null,
                        "John",
                        "Doe",
                        "john.doe@example.com",
                        "john_doe",
                        passwordEncoder.encode("password123"),
                        Role.USER
                ));

                patientRepository.save(new Patient(
                        null,
                        "Jane",
                        "Smith",
                        "jane.smith@example.com",
                        "jane_smith",
                        passwordEncoder.encode("securePassword"),
                        Role.USER
                ));

                patientRepository.save(new Patient(
                        null,
                        "Admin",
                        "User",
                        "admin@example.com",
                        "admin",
                        passwordEncoder.encode("adminPass"),
                        Role.ADMIN
                ));
            }
        };
    }
}
