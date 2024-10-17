package com.damian.figinski.dentistapp.controller;

import com.damian.figinski.dentistapp.model.Patient;
import com.damian.figinski.dentistapp.service.PatientService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor Injection
    public PatientController(PatientService patientService, BCryptPasswordEncoder passwordEncoder) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Patient registerPatient(@RequestBody Patient patient) {
        // Hasło musi być zakodowane
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientService.save(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePatientById(@PathVariable Long id) {
        patientService.deleteById(id);
    }
}
