package com.joaodev.labdesignpatternsspring.service.state;

import com.joaodev.labdesignpatternsspring.domain.Order;

public class PaidState extends BaseOrderState {

    @Override
    public OrderState ship(Order order) {
        return new ShippedState();
    }

    @Override
    public OrderState cancel(Order order) {
        return new CancelledState();
    }

    @Override
    public String getStatusName() {
        return "PAID";
    }
}
