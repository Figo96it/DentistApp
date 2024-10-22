package com.damian.figinski.dentistapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate appointmentDate; // Data wizyty
    private LocalTime appointmentTime; // Godzina wizyty

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private User dentist;  // Dentysta, u którego odbywa się wizyta

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;  // Pacjent, który umawia się na wizytę

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service; // Wybrana usługa na wizytę

    private String status; // Status wizyty (np. zaplanowana, odbyta, odwołana)
}
