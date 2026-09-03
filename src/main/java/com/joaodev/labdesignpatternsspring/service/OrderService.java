package com.joaodev.labdesignpatternsspring.service;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderStatus;
import com.joaodev.labdesignpatternsspring.exception.OrderNotFoundException;
import com.joaodev.labdesignpatternsspring.repository.OrderRepository;
import com.joaodev.labdesignpatternsspring.service.event.OrderStatusChangedEvent;
import com.joaodev.labdesignpatternsspring.service.state.OrderState;
import com.joaodev.labdesignpatternsspring.service.state.OrderStateFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order pay(Long orderId) {
        return executeTransition(orderId, OrderState::pay);
    }

    public Order ship(Long orderId) {
        return executeTransition(orderId, OrderState::ship);
    }

    public Order deliver(Long orderId) {
        return executeTransition(orderId, OrderState::deliver);
    }

    public Order cancel(Long orderId) {
        return executeTransition(orderId, OrderState::cancel);
    }

    private Order executeTransition(Long orderId, BiFunction<OrderState, Order, OrderState> action) {
        Order order = findOrderOrThrow(orderId);
        OrderStatus previousStatus = order.getStatus();
        OrderState currentState = OrderStateFactory.from(previousStatus);
        OrderState nextState = action.apply(currentState, order);
        return applyStatusChange(order, previousStatus, nextState);
    }

    private Order findOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não localizado com o id: " + id));
    }

    private Order applyStatusChange(Order order, OrderStatus previousStatus, OrderState nextState) {
        OrderStatus newStatus = OrderStatus.valueOf(nextState.getStatusName());
        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(saved, previousStatus, newStatus));
        return saved;
    }
}
