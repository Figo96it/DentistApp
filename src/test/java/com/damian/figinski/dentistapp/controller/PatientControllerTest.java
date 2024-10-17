package com.damian.figinski.dentistapp.controller;

import com.damian.figinski.dentistapp.TestSecurityConfig;
import com.damian.figinski.dentistapp.model.Patient;
import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PatientController.class)
@Import(TestSecurityConfig.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        patient1 = new Patient(1L, "John", "Doe", "john.doe@example.com", "john_doe", "password123", Role.USER);
        patient2 = new Patient(2L, "Jane", "Doe", "jane.doe@example.com", "jane_doe", "password456", Role.USER);
    }

    @Test
    void testRegisterPatient() throws Exception {
        // Given
        Patient patientToRegister = new Patient(null, "John", "Doe", "john.doe@example.com", "john_doe", "password123", Role.USER);
        Patient registeredPatient = new Patient(1L, "John", "Doe", "john.doe@example.com", "john_doe", "encoded_password", Role.USER);

        when(passwordEncoder.encode(patientToRegister.getPassword())).thenReturn("encoded_password");
        when(patientService.save(ArgumentMatchers.any(Patient.class))).thenReturn(registeredPatient);

        // When & Then
        mockMvc.perform(post("/api/patients/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientToRegister)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.password", is("encoded_password")));

        verify(patientService, times(1)).save(ArgumentMatchers.any(Patient.class));
    }

    @Test
    void testGetAllPatients() throws Exception {
        // Given
        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.findAll()).thenReturn(patients);

        // When & Then
        mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));

        verify(patientService, times(1)).findAll();
    }

    @Test
    void testCreatePatient() throws Exception {
        // Given
        Patient patientToCreate = new Patient(null, "Jane", "Doe", "jane.doe@example.com", "jane_doe", "password456", Role.USER);
        when(patientService.save(ArgumentMatchers.any(Patient.class))).thenReturn(patient2);

        // When & Then
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("Jane")));

        verify(patientService, times(1)).save(ArgumentMatchers.any(Patient.class));
    }

    @Test
    void testGetPatientById() throws Exception {
        // Given
        Long id = 1L;
        when(patientService.findById(id)).thenReturn(patient1);

        // When & Then
        mockMvc.perform(get("/api/patients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(patientService, times(1)).findById(id);
    }

    @Test
    void testDeletePatientById() throws Exception {
        // Given
        Long id = 1L;
        doNothing().when(patientService).deleteById(id);

        // When & Then
        mockMvc.perform(delete("/api/patients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(patientService, times(1)).deleteById(id);
    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
