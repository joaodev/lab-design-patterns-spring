package com.joaodev.labdesignpatternsspring.service.payment;

import com.joaodev.labdesignpatternsspring.dto.ExternalGatewayResponse;
import com.joaodev.labdesignpatternsspring.service.payment.gateway.ExternalPaymentGatewayClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentGatewayAdapter implements PaymentProcessor {

    private final ExternalPaymentGatewayClient gatewayClient;

    public PaymentGatewayAdapter(ExternalPaymentGatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    @Override
    public PaymentResult process(BigDecimal amount) {
        ExternalGatewayResponse response = gatewayClient.charge(amount);

        boolean approved = "SUCCESS".equals(response.status());

        return new PaymentResult(
                approved,
                response.refId(),
                response.description()
        );
    }
}