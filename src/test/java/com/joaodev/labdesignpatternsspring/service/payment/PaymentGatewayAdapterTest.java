package com.joaodev.labdesignpatternsspring.service.payment;

import com.joaodev.labdesignpatternsspring.dto.ExternalGatewayResponse;
import com.joaodev.labdesignpatternsspring.service.payment.gateway.ExternalPaymentGatewayClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentGatewayAdapterTest {

    @Mock
    private ExternalPaymentGatewayClient gatewayClient;

    @InjectMocks
    private PaymentGatewayAdapter adapter;

    @Test
    void shouldReturnApprovedWhenGatewayStatusIsSuccess() {
        var response = new ExternalGatewayResponse(
                "SUCCESS", "EXT-12345678", "Transação aprovada pelo gateway"
        );
        when(gatewayClient.charge(new BigDecimal("500.00"))).thenReturn(response);

        PaymentResult result = adapter.process(new BigDecimal("500.00"));

        assertThat(result.approved()).isTrue();
        assertThat(result.transactionId()).isEqualTo("EXT-12345678");
        assertThat(result.message()).isEqualTo("Transação aprovada pelo gateway");
    }

    @Test
    void shouldReturnNotApprovedWhenGatewayStatusIsWaiting() {
        var response = new ExternalGatewayResponse(
                "WAITING", "EXT-87654321", "Em análise de risco devido ao valor elevado"
        );
        when(gatewayClient.charge(new BigDecimal("1500.00"))).thenReturn(response);

        PaymentResult result = adapter.process(new BigDecimal("1500.00"));

        assertThat(result.approved()).isFalse();
        assertThat(result.transactionId()).isEqualTo("EXT-87654321");
    }
}