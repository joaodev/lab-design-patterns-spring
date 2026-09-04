package com.joaodev.labdesignpatternsspring.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String name) {
        super("Estoque insuficiente para o produto: " + name);
    }
}
