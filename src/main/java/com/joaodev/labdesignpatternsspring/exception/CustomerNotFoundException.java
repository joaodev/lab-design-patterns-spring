package com.joaodev.labdesignpatternsspring.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Cliente não localizado com o id: " + id);
    }
}