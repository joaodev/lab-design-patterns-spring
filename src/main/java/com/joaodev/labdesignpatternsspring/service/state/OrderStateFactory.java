package com.joaodev.labdesignpatternsspring.service.state;

import com.joaodev.labdesignpatternsspring.domain.OrderStatus;

import static com.joaodev.labdesignpatternsspring.domain.OrderStatus.CREATED;

public class OrderStateFactory {

    public static OrderState from(OrderStatus status) {
        return switch (status) {
            case CREATED -> new CreatedState();
            case PAID ->  new PaidState();
            case SHIPPED -> new ShippedState();
            case DELIVERED -> new DeliveredState();
            case CANCELLED -> new CancelledState();
        };
    }
}
