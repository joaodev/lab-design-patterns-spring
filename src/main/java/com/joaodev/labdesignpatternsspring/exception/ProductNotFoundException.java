package com.joaodev.labdesignpatternsspring.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Produto não localizado com o id: " + id);
    }
}