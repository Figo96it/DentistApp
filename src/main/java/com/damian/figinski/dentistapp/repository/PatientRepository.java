package com.damian.figinski.dentistapp.repository;

import com.damian.figinski.dentistapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // You can add custom query methods here, for example: findByEmail
    Optional<Patient> findByUsername(String username);
}
