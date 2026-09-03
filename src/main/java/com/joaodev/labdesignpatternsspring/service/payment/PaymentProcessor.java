package com.joaodev.labdesignpatternsspring.service.payment;

import java.math.BigDecimal;

public interface PaymentProcessor {
    PaymentResult process(BigDecimal amount);
}
