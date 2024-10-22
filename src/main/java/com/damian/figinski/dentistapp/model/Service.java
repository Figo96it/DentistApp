package com.damian.figinski.dentistapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Nazwa usługi (np. wypełnienie ubytku)
    private String description; // Opis usługi
    private Double price;       // Cena usługi
    private int duration;       // Czas trwania usługi w minutach

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private User dentist; // Relacja do dentysty, który oferuje tę usługę
}
