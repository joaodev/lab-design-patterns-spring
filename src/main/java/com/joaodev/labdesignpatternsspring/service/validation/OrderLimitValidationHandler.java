package com.joaodev.labdesignpatternsspring.service.validation;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.exception.OrderLimitExceededException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@org.springframework.core.annotation.Order(2)
public class OrderLimitValidationHandler extends OrderValidationHandler {

    private static final BigDecimal PURCHASE_LIMIT = new BigDecimal("10000.00");

    @Override
    protected void doValidate(Order order) {
        if (order.getTotal().compareTo(PURCHASE_LIMIT) > 0) {
            throw new OrderLimitExceededException(order.getId());
        }
    }
}
