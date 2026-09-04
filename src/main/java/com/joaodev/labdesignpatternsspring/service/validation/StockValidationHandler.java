package com.joaodev.labdesignpatternsspring.service.validation;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderItem;
import com.joaodev.labdesignpatternsspring.exception.InsufficientStockException;
import org.springframework.stereotype.Component;

@Component
@org.springframework.core.annotation.Order(1)
public class StockValidationHandler extends OrderValidationHandler {

    @Override
    protected void doValidate(Order order) {
        for (OrderItem item : order.getItems()) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                throw new InsufficientStockException(item.getProduct().getName());
            }
        }
    }
}
