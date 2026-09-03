package com.joaodev.labdesignpatternsspring.service.state;

import com.joaodev.labdesignpatternsspring.domain.Order;

public class ShippedState extends BaseOrderState {

    @Override
    public OrderState deliver(Order order) {
        return new DeliveredState();
    }

    @Override
    public String getStatusName() {
        return "SHIPPED";
    }
}
