package com.joaodev.labdesignpatternsspring.service.shipping;

import com.joaodev.labdesignpatternsspring.domain.Order;

import java.math.BigDecimal;

public class InsuranceShippingDecorator extends ShippingDecorator {

    private static final BigDecimal INSURANCE_FEE = new BigDecimal("5.00");

    public InsuranceShippingDecorator(ShippingStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public BigDecimal calculate(Order order) {
        BigDecimal baseShipping = wrapped.calculate(order);
        return baseShipping.add(INSURANCE_FEE);
    }
}
