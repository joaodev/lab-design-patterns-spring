package com.joaodev.labdesignpatternsspring.service.shipping;

import com.joaodev.labdesignpatternsspring.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("FREE")
public class FreeShippingStrategy implements ShippingStrategy {
    private static final BigDecimal MINIMUM_FOR_FREE_SHIPPING = new BigDecimal("200.00");

    @Override
    public BigDecimal calculate(Order order) {
        if (order.getTotal().compareTo(MINIMUM_FOR_FREE_SHIPPING) >= 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("15.00");
    }
}
