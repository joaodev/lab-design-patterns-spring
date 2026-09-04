package com.joaodev.labdesignpatternsspring.service.payment;

import com.joaodev.labdesignpatternsspring.domain.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessorFactory {

    private final PixPaymentProcessor pixPaymentProcessor;
    private final CreditCardPaymentProcessor creditCardPaymentProcessor;
    private final BankSlipPaymentProcessor bankSlipPaymentProcessor;
    private final PaymentGatewayAdapter paymentGatewayAdapter;

    public PaymentProcessorFactory(PixPaymentProcessor pixPaymentProcessor,
                                   CreditCardPaymentProcessor creditCardPaymentProcessor,
                                   BankSlipPaymentProcessor bankSlipPaymentProcessor,
                                   PaymentGatewayAdapter paymentGatewayAdapter) {
        this.pixPaymentProcessor = pixPaymentProcessor;
        this.creditCardPaymentProcessor = creditCardPaymentProcessor;
        this.bankSlipPaymentProcessor = bankSlipPaymentProcessor;
        this.paymentGatewayAdapter = paymentGatewayAdapter;
    }

    public PaymentProcessor create(PaymentType type) {
        return switch (type) {
            case PIX -> pixPaymentProcessor;
            case CREDIT_CARD -> creditCardPaymentProcessor;
            case BANK_SLIP -> bankSlipPaymentProcessor;
            case EXTERNAL_GATEWAY -> paymentGatewayAdapter;
        };
    }
}
