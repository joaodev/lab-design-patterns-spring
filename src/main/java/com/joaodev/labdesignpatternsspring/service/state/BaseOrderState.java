package com.joaodev.labdesignpatternsspring.service.state;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.exception.InvalidOrderStateTransitionException;

public abstract class BaseOrderState implements OrderState {

    @Override
    public OrderState pay(Order order) {
        throw invalidTransition("pagar");
    }

    @Override
    public OrderState ship(Order order) {
        throw invalidTransition("enviar");
    }

    @Override
    public OrderState deliver(Order order) {
        throw invalidTransition("entregar");
    }

    @Override
    public OrderState cancel(Order order) {
        throw invalidTransition("cancelar");
    }

    private InvalidOrderStateTransitionException invalidTransition(String action) {
        return new InvalidOrderStateTransitionException(action, getStatusName());
    }
}
