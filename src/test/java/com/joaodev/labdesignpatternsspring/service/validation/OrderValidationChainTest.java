package com.joaodev.labdesignpatternsspring.service.validation;

import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.OrderItem;
import com.joaodev.labdesignpatternsspring.domain.OrderStatus;
import com.joaodev.labdesignpatternsspring.domain.Product;
import com.joaodev.labdesignpatternsspring.exception.InsufficientStockException;
import com.joaodev.labdesignpatternsspring.exception.OrderLimitExceededException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class OrderValidationChainTest {

    @Autowired
    private OrderValidationChain validationChain;

    @Test
    void shouldPassWhenOrderIsValid() {
        Order order = buildOrderWithTotal(new BigDecimal("500.00"), true);

        assertThatCode(() -> validationChain.validate(order))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowWhenStockIsInsufficient() {
        Order order = buildOrderWithTotal(new BigDecimal("500.00"), false);

        assertThatThrownBy(() -> validationChain.validate(order))
                .isInstanceOf(InsufficientStockException.class);
    }

    @Test
    void shouldThrowWhenLimitIsExceeded() {
        Order order = buildOrderWithTotal(new BigDecimal("15000.00"), true);

        assertThatThrownBy(() -> validationChain.validate(order))
                .isInstanceOf(OrderLimitExceededException.class);
    }

    private Order buildOrderWithTotal(BigDecimal total, boolean stockOk) {
        Product product = Product.builder()
                .name("Produto Teste")
                .description("Descrição de teste")
                .price(new BigDecimal("50.00"))
                .category("Categoria Teste")
                .stockQuantity(stockOk ? 100 : 0)
                .build();

        OrderItem item = OrderItem.builder()
                .product(product)
                .quantity(1)
                .unitPrice(new BigDecimal("50.00"))
                .build();

        Order order = Order.builder()
                .status(OrderStatus.CREATED)
                .total(total)
                .items(List.of(item))
                .build();

        item.setOrder(order);
        return order;
    }
}
