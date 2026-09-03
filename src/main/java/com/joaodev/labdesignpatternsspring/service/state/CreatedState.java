package com.joaodev.labdesignpatternsspring.service.state;

import com.joaodev.labdesignpatternsspring.domain.Order;

public class CreatedState extends BaseOrderState {

    @Override
    public OrderState pay(Order order) {
        return new PaidState();
    }

    @Override
    public OrderState cancel(Order order) {
        return new CancelledState();
    }

    @Override
    public String getStatusName() {
        return "CREATED";
    }
}
