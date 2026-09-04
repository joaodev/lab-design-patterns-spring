package com.joaodev.labdesignpatternsspring.dto;

import com.joaodev.labdesignpatternsspring.domain.Product;

public record ItemRequest(Product product, int quantity) {
}
