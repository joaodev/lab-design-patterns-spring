package com.joaodev.labdesignpatternsspring.service.state;

import com.joaodev.labdesignpatternsspring.domain.Order;

public interface OrderState {
    OrderState pay(Order order);
    OrderState ship(Order order);
    OrderState deliver(Order order);
    OrderState cancel(Order order);
    String getStatusName();
}
