package com.joaodev.labdesignpatternsspring.repository;

import com.joaodev.labdesignpatternsspring.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
