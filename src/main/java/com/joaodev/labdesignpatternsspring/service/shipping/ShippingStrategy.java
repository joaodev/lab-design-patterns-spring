package com.joaodev.labdesignpatternsspring.service.shipping;

import com.joaodev.labdesignpatternsspring.domain.Order;

import java.math.BigDecimal;

public interface ShippingStrategy {
    BigDecimal calculate(Order order);
}
