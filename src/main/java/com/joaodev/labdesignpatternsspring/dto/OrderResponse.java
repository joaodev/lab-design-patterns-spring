package com.joaodev.labdesignpatternsspring.dto;

import com.joaodev.labdesignpatternsspring.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long customerId,
        String customerName,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
}