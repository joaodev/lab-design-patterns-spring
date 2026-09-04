package com.joaodev.labdesignpatternsspring.exception;

public class ShippingStrategyNotFoundException extends RuntimeException {
    public ShippingStrategyNotFoundException(String name) {
        super("Nenhuma estratégia de frete encontrada para o tipo: " + name);
    }
}
