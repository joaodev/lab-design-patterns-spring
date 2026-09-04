package com.joaodev.labdesignpatternsspring.service.validation;

import com.joaodev.labdesignpatternsspring.domain.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderValidationChain {

    private final List<OrderValidationHandler> handlers;
    private OrderValidationHandler firstHandler;

    public OrderValidationChain(List<OrderValidationHandler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    public void buildChain() {
        for (int i = 0; i < handlers.size() -1; i++) {
            handlers.get(i).setNext(handlers.get(i + 1));
        }
        firstHandler = handlers.isEmpty() ? null : handlers.getFirst();
    }

    public void validate(Order order) {
        if (firstHandler != null) {
            firstHandler.validate(order);
        }
    }
}
