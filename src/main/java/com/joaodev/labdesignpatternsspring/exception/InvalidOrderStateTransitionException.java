package com.joaodev.labdesignpatternsspring.exception;

public class InvalidOrderStateTransitionException extends RuntimeException {
    public InvalidOrderStateTransitionException(String message) {
        super(message);
    }
}
