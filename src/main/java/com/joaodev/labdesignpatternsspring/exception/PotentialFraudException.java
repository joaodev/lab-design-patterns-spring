package com.joaodev.labdesignpatternsspring.exception;

public class PotentialFraudException extends RuntimeException {
    public PotentialFraudException(Long orderId) {
        super("Padrão suspeito de compra em massa no pedido: " + orderId);
    }
}
