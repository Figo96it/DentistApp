package com.damian.figinski.dentistapp.repository;

import com.damian.figinski.dentistapp.model.Role;
import com.damian.figinski.dentistapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods here, for example: findByEmail
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);
}
