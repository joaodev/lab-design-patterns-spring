package com.joaodev.labdesignpatternsspring.service.shipping;

import com.joaodev.labdesignpatternsspring.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("EXPRESS")
public class ExpressShippingStrategy implements ShippingStrategy {
    @Override
    public BigDecimal calculate(Order order) {
        return new BigDecimal("30.00");
    }
}
