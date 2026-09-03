package com.joaodev.labdesignpatternsspring.service.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PixPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentResult process(BigDecimal amount) {
        var transactionId = UUID.randomUUID().toString().substring(0, 10);
        return new PaymentResult(
                true,
                transactionId,
                "Pagamento realizado"
        );
    }
}
