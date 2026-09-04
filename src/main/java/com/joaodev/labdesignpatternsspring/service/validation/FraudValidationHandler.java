package com.joaodev.labdesignpatternsspring.service.validation;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.exception.PotentialFraudException;
import org.springframework.stereotype.Component;

@Component
@org.springframework.core.annotation.Order(3)
public class FraudValidationHandler extends OrderValidationHandler {

    @Override
    protected void doValidate(Order order) {
        if (order.getItems().size() > 10) {
            throw new PotentialFraudException(order.getId());
        }
    }
}
