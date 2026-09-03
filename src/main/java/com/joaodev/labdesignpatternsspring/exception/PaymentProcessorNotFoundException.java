package com.joaodev.labdesignpatternsspring.exception;

public class PaymentProcessorNotFoundException extends RuntimeException {
    public PaymentProcessorNotFoundException(String message) {
        super(message);
    }
}
