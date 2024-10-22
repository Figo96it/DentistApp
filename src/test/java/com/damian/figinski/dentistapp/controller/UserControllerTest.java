package com.damian.figinski.dentistapp.controller;

import com.damian.figinski.dentistapp.TestSecurityConfig;
import com.damian.figinski.dentistapp.controller.patient.PatientController;
import com.damian.figinski.dentistapp.model.User;
import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "john_doe",
                "password123",
                Role.PATIENT,
                "555-1234",    // Phone number
                "123 Main St", // Address
                "Cityville",   // City
                "12345",       // Zip code
                null,          // Clinic address (not applicable for patient)
                null,          // Specialization (not applicable for patient)
                null,          // Education (not applicable for patient)
                null,          // Experience (not applicable for patient)
                null,          // Services offered (not applicable for patient)
                null,          // Schedule (not applicable for patient)
                null,          // Appointments
                null
        );

        user2 = new User(
                2L,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "jane_doe",
                "password456",
                Role.PATIENT,
                "555-5678",    // Phone number
                "456 Another St", // Address
                "Townsville",   // City
                "67890",       // Zip code
                null,          // Clinic address (not applicable for patient)
                null,          // Specialization (not applicable for patient)
                null,          // Education (not applicable for patient)
                null,          // Experience (not applicable for patient)
                null,          // Services offered (not applicable for patient)
                null,          // Schedule (not applicable for patient)
                null,           // Appointments
                null
        );
    }

    @Test
    void testGetAllPatients() throws Exception {
        // Given
        List<User> users = Arrays.asList(user1, user2);
        when(patientService.findAll()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].phoneNumber", is("555-1234"))) // Nowe pole
                .andExpect(jsonPath("$[0].address", is("123 Main St"))) // Nowe pole
                .andExpect(jsonPath("$[1].firstName", is("Jane")))
                .andExpect(jsonPath("$[1].phoneNumber", is("555-5678"))) // Nowe pole
                .andExpect(jsonPath("$[1].address", is("456 Another St"))); // Nowe pole

        verify(patientService, times(1)).findAll();
    }

    @Test
    void testGetPatientById() throws Exception {
        // Given
        Long id = 1L;
        when(patientService.findById(id)).thenReturn(user1);

        // When & Then
        mockMvc.perform(get("/api/patients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.phoneNumber", is("555-1234"))) // Nowe pole
                .andExpect(jsonPath("$.address", is("123 Main St"))); // Nowe pole

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

