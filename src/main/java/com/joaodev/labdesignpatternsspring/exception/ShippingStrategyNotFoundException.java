package com.joaodev.labdesignpatternsspring.exception;

public class ShippingStrategyNotFoundException extends RuntimeException {
    public ShippingStrategyNotFoundException(String message) {
        super(message);
    }
}
