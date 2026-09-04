package com.joaodev.labdesignpatternsspring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckoutItemRequest(
        @NotNull Long productId,
        @Positive int quantity
) {
}