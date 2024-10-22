package com.damian.figinski.dentistapp.controller.patient;

import com.damian.figinski.dentistapp.model.User;
import com.damian.figinski.dentistapp.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    // Constructor Injection
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<User> getAllPatients() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public User getPatientById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePatientById(@PathVariable Long id) {
        patientService.deleteById(id);
    }
}
