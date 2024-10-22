package com.damian.figinski.dentistapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private User dentist; // Dentysta, do którego dotyczy ten harmonogram

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // Dzień tygodnia (np. PONIEDZIAŁEK)

    private LocalTime startTime;  // Godzina rozpoczęcia pracy dentysty tego dnia
    private LocalTime endTime;    // Godzina zakończenia pracy dentysty tego dnia

    private int appointmentDuration; // Domyślny czas trwania wizyt w minutach (np. 30 minut)
}
