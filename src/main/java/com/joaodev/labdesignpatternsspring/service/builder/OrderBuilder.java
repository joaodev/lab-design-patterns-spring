package com.joaodev.labdesignpatternsspring.service.builder;

import com.joaodev.labdesignpatternsspring.domain.*;
import com.joaodev.labdesignpatternsspring.exception.InsufficientStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {

    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();

    private OrderBuilder(Customer customer) {
        this.customer = customer;
    }

    public static OrderBuilder forCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer não pode ser nulo");
        }
        return new OrderBuilder(customer);
    }

    public OrderBuilder addItem(Product product, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Estoque insuficiente para o produto: " + product.getName()
            );
        }

        OrderItem item = OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .build();

        items.add(item);
        return this;
    }

    public Order build() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Pedido precisa ter ao menos um item");
        }

        BigDecimal total = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.CREATED)
                .total(total)
                .build();

        items.forEach(item -> item.setOrder(order));
        order.setItems(items);

        return order;
    }
}
