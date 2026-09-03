package com.joaodev.labdesignpatternsspring.service.payment;

public record PaymentResult(boolean approved, String transactionId, String message) {
}
