package com.joaodev.labdesignpatternsspring.exception;

public class OrderLimitExceededException extends RuntimeException {
    public OrderLimitExceededException(Long orderId) {
        super("Limite excedido para o pedido " + orderId);
    }
}
