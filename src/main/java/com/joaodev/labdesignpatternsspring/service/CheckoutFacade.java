package com.joaodev.labdesignpatternsspring.service;

import com.joaodev.labdesignpatternsspring.domain.*;
import com.joaodev.labdesignpatternsspring.repository.OrderRepository;
import com.joaodev.labdesignpatternsspring.repository.PaymentRepository;
import com.joaodev.labdesignpatternsspring.service.builder.OrderBuilder;
import com.joaodev.labdesignpatternsspring.dto.ItemRequest;
import com.joaodev.labdesignpatternsspring.service.payment.PaymentProcessor;
import com.joaodev.labdesignpatternsspring.service.payment.PaymentProcessorFactory;
import com.joaodev.labdesignpatternsspring.service.payment.PaymentResult;
import com.joaodev.labdesignpatternsspring.service.validation.OrderValidationChain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutFacade {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderValidationChain validationChain;
    private final ShippingCalculator shippingCalculator;
    private final OrderService orderService;
    private final PaymentProcessorFactory paymentProcessorFactory;

    public CheckoutFacade(OrderRepository orderRepository, PaymentRepository paymentRepository,
                          OrderValidationChain validationChain, ShippingCalculator shippingCalculator,
                          OrderService orderService, PaymentProcessorFactory paymentProcessorFactory) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.validationChain = validationChain;
        this.shippingCalculator = shippingCalculator;
        this.orderService = orderService;
        this.paymentProcessorFactory = paymentProcessorFactory;
    }

    public Order checkout(Customer customer, List<ItemRequest> items, ShippingType shippingType, PaymentType paymentType) {
        OrderBuilder builder = OrderBuilder.forCustomer(customer);
        for (ItemRequest item: items) {
            builder.addItem(item.product(), item.quantity());
        }
        Order order = builder.build();
        validationChain.validate(order);

        var shippingCost = shippingCalculator.calculate(order, shippingType);
        order.setTotal(order.getTotal().add(shippingCost));
        Order savedOrder = orderRepository.save(order);

        PaymentProcessor processor = paymentProcessorFactory.create(paymentType);
        PaymentResult result = processor.process(savedOrder.getTotal());

        Payment payment = Payment.builder()
                .order(savedOrder)
                .paymentTypeMethod(paymentType.name())
                .amount(savedOrder.getTotal())
                .status(result.approved() ? PaymentStatus.APPROVED : PaymentStatus.PENDING)
                .build();
        paymentRepository.save(payment);

        if (result.approved()) {
            return orderService.pay(savedOrder.getId());
        }

        return savedOrder;
    }
}
