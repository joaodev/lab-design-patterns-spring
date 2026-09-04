package com.joaodev.labdesignpatternsspring.service.shipping;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class InsuranceShippingDecoratorTest {

    @Test
    void shouldAddInsuranceFeeOnTopOfBaseShipping() {
        ShippingStrategy fakeBase = order -> new BigDecimal("15.00");
        ShippingStrategy decorated = new InsuranceShippingDecorator(fakeBase);
        BigDecimal result = decorated.calculate(null);
        assertThat(result).isEqualByComparingTo(new BigDecimal("20.00"));
    }
}
