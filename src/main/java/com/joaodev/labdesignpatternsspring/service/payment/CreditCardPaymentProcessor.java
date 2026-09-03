package com.joaodev.labdesignpatternsspring.service.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CreditCardPaymentProcessor implements PaymentProcessor {
    private static final BigDecimal CREDIT_LIMIT = new BigDecimal("50000.00");

    @Override
    public PaymentResult process(BigDecimal amount) {
        boolean approved = amount.compareTo(CREDIT_LIMIT) < 0;
        var transactionId = UUID.randomUUID().toString().substring(0, 10);

        String message = approved
                ? "Pagamento aprovado"
                : "Pagamento recusado: limite excedido";

        return new PaymentResult(approved, transactionId, message);
    }
}
