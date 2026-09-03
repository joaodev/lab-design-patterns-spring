package com.joaodev.labdesignpatternsspring.repository;

import com.joaodev.labdesignpatternsspring.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
