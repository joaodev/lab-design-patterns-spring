package com.joaodev.labdesignpatternsspring.dto;

import com.joaodev.labdesignpatternsspring.domain.PaymentType;
import com.joaodev.labdesignpatternsspring.domain.ShippingType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CheckoutRequest(
        @NotNull Long customerId,
        @NotEmpty @Valid List<CheckoutItemRequest> items,
        @NotNull ShippingType shippingType,
        @NotNull PaymentType paymentType
) {
}