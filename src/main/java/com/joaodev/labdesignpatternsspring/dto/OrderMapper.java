package com.joaodev.labdesignpatternsspring.dto;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderItem;

import java.util.List;

public class OrderMapper {

    private OrderMapper() {}

    public static OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(OrderMapper::toItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                items
        );
    }

    private static OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );
    }
}