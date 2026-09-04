package com.joaodev.labdesignpatternsspring.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Pedido não localizado com o id: " + orderId);
    }
}
