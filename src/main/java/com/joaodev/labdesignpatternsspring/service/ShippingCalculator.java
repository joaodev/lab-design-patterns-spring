package com.joaodev.labdesignpatternsspring.service;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.ShippingType;
import com.joaodev.labdesignpatternsspring.exception.ShippingStrategyNotFoundException;
import com.joaodev.labdesignpatternsspring.service.shipping.ShippingStrategy;

import java.math.BigDecimal;
import java.util.Map;

public class ShippingCalculator {

    private final Map<String, ShippingStrategy> strategies;

    public ShippingCalculator(Map<String, ShippingStrategy> strategies) {
        this.strategies = strategies;
    }

    public BigDecimal calculate(Order order, ShippingType type) {
        ShippingStrategy strategy = strategies.get(type.name());

        if (strategy == null) {
            throw new ShippingStrategyNotFoundException(
                    "Nenhuma estratégia de frete encontrada para o tipo: " + type.name()
            );
        }

        return strategy.calculate(order);
    }
}
