package com.damian.figinski.dentistapp.config;

import com.damian.figinski.dentistapp.model.*;
import com.damian.figinski.dentistapp.repository.UserRepository;
import com.damian.figinski.dentistapp.repository.ServiceRepository;
import com.damian.figinski.dentistapp.repository.ScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(UserRepository userRepository,
                               ServiceRepository serviceRepository,
                               ScheduleRepository scheduleRepository,
                               BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {

                // Pacjent
                User johnDoe = new User(
                        null,
                        "John",
                        "Doe",
                        "john.doe@example.com",
                        "john_doe",
                        passwordEncoder.encode("password123"),
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
                        null,           // Appointments
                        null
                );
                userRepository.save(johnDoe);

                // Dentysta
                User janeSmith = new User(
                        null,
                        "Jane",
                        "Smith",
                        "jane.smith@example.com",
                        "jane_smith",
                        passwordEncoder.encode("securePassword"),
                        Role.DENTIST,
                        "555-5678",            // Phone number
                        null,                  // Address (not applicable for dentist)
                        null,                  // City (not applicable for dentist)
                        null,                  // Zip code (not applicable for dentist)
                        "456 Dental Ave",      // Clinic address
                        "Orthodontics",        // Specialization
                        "Dental School ABC",   // Education
                        "10 years experience", // Experience
                        null,                  // Services (will be set later)
                        null,                  // Schedule (will be set later)
                        null,                   // Appointments (will be set later)
                        null
                );
                userRepository.save(janeSmith);

                // Admin
                User admin = new User(
                        null,
                        "Admin",
                        "User",
                        "admin@example.com",
                        "admin",
                        passwordEncoder.encode("adminPass"),
                        Role.ADMIN,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                userRepository.save(admin);

                // Dodanie usług dla dentysty Jane Smith
                Service cleaning = new Service(null, "Teeth Cleaning", "Standard teeth cleaning service", 100.0, 30, janeSmith);
                Service whitening = new Service(null, "Teeth Whitening", "Teeth whitening treatment", 150.0, 60, janeSmith);
                serviceRepository.saveAll(List.of(cleaning, whitening));

                // Dodanie harmonogramu pracy dla dentysty Jane Smith
                Schedule scheduleMonday = new Schedule(null, janeSmith, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0), 30);
                Schedule scheduleTuesday = new Schedule(null, janeSmith, DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0), 30);
                scheduleRepository.saveAll(List.of(scheduleMonday, scheduleTuesday));

            }
        };
    }
}
