package com.damian.figinski.dentistapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;  // Dodanie pola roli użytkownika

    // Pola wspólne dla pacjentów i dentystów
    private String phoneNumber;

    // Pola specyficzne dla pacjentów
    private String address;
    private String city;
    private String zipCode;

    // Pola specyficzne dla dentystów
    private String clinicAddress;
    private String specialization;
    private String education;
    private String experience;

    // ----- Relacje specyficzne dla dentysty -----

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Service> servicesOffered; // Lista usług oferowanych przez dentystę

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Schedule> schedules; // Harmonogram dostępności dentysty

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments; // Wizyty umawiane przez pacjentów

    // ----- Relacje specyficzne dla pacjenta -----

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> patientAppointments; // Wizyty umówione przez pacjenta

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + role.name());
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
