package com.joaodev.labdesignpatternsspring.service;

import com.joaodev.labdesignpatternsspring.domain.Customer;
import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderStatus;
import com.joaodev.labdesignpatternsspring.exception.InvalidOrderStateTransitionException;
import com.joaodev.labdesignpatternsspring.repository.CustomerRepository;
import com.joaodev.labdesignpatternsspring.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldChangeOrderStatusAndTriggerListeners() {
        Customer customer = Customer.builder()
                .name("Cliente")
                .email("cliente@teste.com")
                .phone("123123123")
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        Order order = Order.builder()
                .customer(savedCustomer)
                .status(OrderStatus.CREATED)
                .total(new BigDecimal("100.00"))
                .build();
        Order savedOrder = orderRepository.save(order);
        Order result = orderService.pay(savedOrder.getId());

        assertThat(result.getStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void shouldThrowExceptionWhenTransitionIsInvalid() {
        Customer customer = Customer.builder()
                .name("Cliente")
                .email("cliente2@teste.com")
                .phone("123123123")
                .build();
        Customer savedCustomer = customerRepository.save(customer);

        Order order = Order.builder()
                .customer(savedCustomer)
                .status(OrderStatus.CREATED)
                .total(new BigDecimal("100.00"))
                .build();
        Order savedOrder = orderRepository.save(order);

        assertThatThrownBy(() -> orderService.ship(savedOrder.getId()))
                .isInstanceOf(InvalidOrderStateTransitionException.class)
                .hasMessageContaining("Não é possível enviar um pedido no estado CREATED");
    }
}
