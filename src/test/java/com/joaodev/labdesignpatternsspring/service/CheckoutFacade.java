package com.joaodev.labdesignpatternsspring.service;

import com.joaodev.labdesignpatternsspring.domain.*;
import com.joaodev.labdesignpatternsspring.repository.CustomerRepository;
import com.joaodev.labdesignpatternsspring.repository.ProductRepository;
import com.joaodev.labdesignpatternsspring.service.dto.ItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CheckoutFacadeTest {

    @Autowired
    private CheckoutFacade checkoutFacade;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldCompleteFullCheckoutWithPixAndBeMarkedAsPaid() {
        Customer customer = customerRepository.save(
                Customer.builder()
                        .name("Cliente Checkout")
                        .email("checkout@teste.com")
                        .phone("123123123")
                        .build()
        );

        Product product = productRepository.save(
                Product.builder()
                        .name("Produto Checkout")
                        .description("Descrição")
                        .price(new BigDecimal("100.00"))
                        .category("Categoria")
                        .stockQuantity(10)
                        .build()
        );

        List<ItemRequest> items = List.of(new ItemRequest(product, 2));
        Order result = checkoutFacade.checkout(customer, items, ShippingType.STANDARD, PaymentType.PIX);

        assertThat(result.getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(result.getTotal()).isEqualByComparingTo(new BigDecimal("215.00")); // 200 (2x100) + 15 (frete standard)
    }

    @Test
    void shouldCompleteCheckoutWithBoletoAndStayCreated() {
        Customer customer = customerRepository.save(
                Customer.builder()
                        .name("Cliente Boleto")
                        .email("boleto@teste.com")
                        .phone("123123123")
                        .build()
        );

        Product product = productRepository.save(
                Product.builder()
                        .name("Produto Boleto")
                        .description("Descrição")
                        .price(new BigDecimal("50.00"))
                        .category("Categoria")
                        .stockQuantity(10)
                        .build()
        );

        List<ItemRequest> items = List.of(new ItemRequest(product, 1));

        Order result = checkoutFacade.checkout(customer, items, ShippingType.STANDARD, PaymentType.BANK_SLIP);

        assertThat(result.getStatus()).isEqualTo(OrderStatus.CREATED);
    }
}