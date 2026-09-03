package com.joaodev.labdesignpatternsspring.service.event;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderStatus;

public record OrderStatusChangedEvent(Order order, OrderStatus previousStatus, OrderStatus newStatus) {
}
