package com.damian.figinski.dentistapp.repository;

import com.damian.figinski.dentistapp.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
