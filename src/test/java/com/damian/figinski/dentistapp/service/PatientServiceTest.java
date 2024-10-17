package com.damian.figinski.dentistapp.service;

import com.damian.figinski.dentistapp.model.Patient;
import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testFindAll() {
        // Given
        Patient patient1 = new Patient(1L, "John", "Doe", "john.doe@example.com", "john_doe", "password123", Role.USER);
        Patient patient2 = new Patient(2L, "Jane", "Doe", "jane.doe@example.com", "jane_doe", "password456", Role.USER);
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        // When
        List<Patient> patients = patientService.findAll();

        // Then
        assertEquals(2, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
        assertEquals("john_doe", patients.get(0).getUsername());
        assertEquals("Jane", patients.get(1).getFirstName());
        assertEquals("jane_doe", patients.get(1).getUsername());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        // Given
        Patient patient = new Patient(null, "John", "Doe", "john.doe@example.com", "john_doe", "password123", Role.USER);
        Patient savedPatientWithId = new Patient(1L, "John", "Doe", "john.doe@example.com", "john_doe", "password123", Role.USER);
        when(patientRepository.save(patient)).thenReturn(savedPatientWithId);

        // When
        Patient savedPatient = patientService.save(patient);

        // Then
        assertNotNull(savedPatient);
        assertEquals(1L, savedPatient.getId());
        assertEquals("John", savedPatient.getFirstName());
        assertEquals("john_doe", savedPatient.getUsername());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        Patient patient = new Patient(id, "John", "Doe", "john.doe@example.com", "john_doe", "password123", Role.USER);
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // When
        Patient foundPatient = patientService.findById(id);

        // Then
        assertNotNull(foundPatient);
        assertEquals("John", foundPatient.getFirstName());
        assertEquals("john_doe", foundPatient.getUsername());
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        Long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Patient foundPatient = patientService.findById(id);

        // Then
        assertNull(foundPatient);
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        // Given
        Long id = 1L;

        // When
        patientService.deleteById(id);

        // Then
        verify(patientRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteById_RepositoryException() {
        // Given
        Long id = 1L;
        doThrow(new IllegalArgumentException("Invalid ID")).when(patientRepository).deleteById(id);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> patientService.deleteById(id));
        verify(patientRepository, times(1)).deleteById(id);
    }
}
