package com.joaodev.labdesignpatternsspring.service.payment.gateway;

import com.joaodev.labdesignpatternsspring.dto.ExternalGatewayResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class ExternalPaymentGatewayClient {

    private static final BigDecimal RISK_THRESHOLD = new BigDecimal("1000.00");

    public ExternalGatewayResponse charge(BigDecimal amount) {
        String refId = "EXT-" + UUID.randomUUID().toString().substring(0, 8);

        if (amount.compareTo(RISK_THRESHOLD) > 0) {
            return new ExternalGatewayResponse("WAITING", refId, "Em análise de risco devido ao valor elevado");
        }

        return new ExternalGatewayResponse("SUCCESS", refId, "Transação aprovada pelo gateway");
    }
}