package com.joaodev.labdesignpatternsspring.exception;

public class InvalidOrderStateTransitionException extends RuntimeException {
    public InvalidOrderStateTransitionException(String action, String name) {
        super("Não é possível " + action + " um pedido no estado " + name);
    }
}
