package com.joaodev.labdesignpatternsspring.service.shipping;

import com.joaodev.labdesignpatternsspring.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("STANDARD")
public class StandardShippingStrategy implements ShippingStrategy {
    @Override
    public BigDecimal calculate(Order order) {
        return new BigDecimal("15.00");
    }
}
