package com.joaodev.labdesignpatternsspring.service.validation;

import com.joaodev.labdesignpatternsspring.domain.Order;
import org.springframework.stereotype.Component;

@Component
public abstract class OrderValidationHandler {

    private OrderValidationHandler next;

    public OrderValidationHandler setNext(OrderValidationHandler next) {
        this.next = next;
        return next;
    }

    public void validate(Order order) {
        doValidate(order);
        if (next != null) {
            next.validate(order);
        }
    }

    protected abstract void doValidate(Order order);
}
