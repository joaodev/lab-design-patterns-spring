package com.joaodev.labdesignpatternsspring.controller;

import com.joaodev.labdesignpatternsspring.domain.Customer;
import com.joaodev.labdesignpatternsspring.domain.Order;
import com.joaodev.labdesignpatternsspring.domain.Product;
import com.joaodev.labdesignpatternsspring.dto.CheckoutRequest;
import com.joaodev.labdesignpatternsspring.dto.ItemRequest;
import com.joaodev.labdesignpatternsspring.dto.OrderMapper;
import com.joaodev.labdesignpatternsspring.dto.OrderResponse;
import com.joaodev.labdesignpatternsspring.exception.CustomerNotFoundException;
import com.joaodev.labdesignpatternsspring.exception.ProductNotFoundException;
import com.joaodev.labdesignpatternsspring.repository.CustomerRepository;
import com.joaodev.labdesignpatternsspring.repository.ProductRepository;
import com.joaodev.labdesignpatternsspring.service.CheckoutFacade;
import com.joaodev.labdesignpatternsspring.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CheckoutFacade checkoutFacade;
    private final OrderService orderService;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderController(
            CheckoutFacade checkoutFacade,
            OrderService orderService,
            CustomerRepository customerRepository,
            ProductRepository productRepository
    ) {
        this.checkoutFacade = checkoutFacade;
        this.orderService = orderService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

        List<ItemRequest> items = request.items().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.productId())
                            .orElseThrow(() -> new ProductNotFoundException(itemRequest.productId()));
                    return new ItemRequest(product, itemRequest.quantity());
                })
                .toList();

        Order order = checkoutFacade.checkout(customer, items, request.shippingType(), request.paymentType());

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toResponse(order));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> pay(@PathVariable Long id) {
        Order order = orderService.pay(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @PostMapping("/{id}/ship")
    public ResponseEntity<OrderResponse> ship(@PathVariable Long id) {
        Order order = orderService.ship(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<OrderResponse> deliver(@PathVariable Long id) {
        Order order = orderService.deliver(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@PathVariable Long id) {
        Order order = orderService.cancel(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }
}