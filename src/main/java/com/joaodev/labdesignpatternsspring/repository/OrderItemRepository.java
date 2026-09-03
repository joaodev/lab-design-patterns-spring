package com.joaodev.labdesignpatternsspring.repository;

import com.joaodev.labdesignpatternsspring.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
