package com.joaodev.labdesignpatternsspring.service;

public class ShippingStrategyNotFoundException extends RuntimeException {
    public ShippingStrategyNotFoundException(String message) {
        super(message);
    }
}
