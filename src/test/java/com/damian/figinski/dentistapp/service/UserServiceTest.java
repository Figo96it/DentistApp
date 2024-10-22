package com.damian.figinski.dentistapp.service;

import com.damian.figinski.dentistapp.model.User;
import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.repository.UserRepository;
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

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testFindAll() {
        // Given
        User user1 = new User(
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
                null,          // Clinic address
                null,          // Specialization
                null,          // Education
                null,          // Experience
                null,          // Services offered
                null,          // Schedule
                null,           // Appointments
                null
        );

        User user2 = new User(
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
                null,          // Clinic address
                null,          // Specialization
                null,          // Education
                null,          // Experience
                null,          // Services offered
                null,          // Schedule
                null,           // Appointments
                null
        );

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<User> users = patientService.findAll();

        // Then
        assertEquals(2, users.size());
        assertEquals("John", users.get(0).getFirstName());
        assertEquals("john_doe", users.get(0).getUsername());
        assertEquals("Jane", users.get(1).getFirstName());
        assertEquals("jane_doe", users.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        // Given
        User user = new User(
                null,
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
                null,          // Clinic address
                null,          // Specialization
                null,          // Education
                null,          // Experience
                null,          // Services offered
                null,          // Schedule
                null,           // Appointments
                null
        );

        User savedUserWithId = new User(
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
                null,          // Clinic address
                null,          // Specialization
                null,          // Education
                null,          // Experience
                null,          // Services offered
                null,          // Schedule
                null,           // Appointments
                null
        );

        when(userRepository.save(user)).thenReturn(savedUserWithId);

        // When
        User savedUser = patientService.save(user);

        // Then
        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals("John", savedUser.getFirstName());
        assertEquals("john_doe", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        User user = new User(
                id,
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
                null,          // Clinic address
                null,          // Specialization
                null,          // Education
                null,          // Experience
                null,          // Services offered
                null,          // Schedule
                null,           // Appointments
                null
        );
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        User foundUser = patientService.findById(id);

        // Then
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstName());
        assertEquals("john_doe", foundUser.getUsername());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When
        User foundUser = patientService.findById(id);

        // Then
        assertNull(foundUser);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        // Given
        Long id = 1L;

        // When
        patientService.deleteById(id);

        // Then
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteById_RepositoryException() {
        // Given
        Long id = 1L;
        doThrow(new IllegalArgumentException("Invalid ID")).when(userRepository).deleteById(id);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> patientService.deleteById(id));
        verify(userRepository, times(1)).deleteById(id);
    }
}
