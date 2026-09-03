package com.joaodev.labdesignpatternsspring.repository;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(OrderStatus status);
}
