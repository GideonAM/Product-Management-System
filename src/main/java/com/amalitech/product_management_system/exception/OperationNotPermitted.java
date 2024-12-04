package com.amalitech.product_management_system.exception;

public class OperationNotPermitted extends RuntimeException {
    public OperationNotPermitted(String message) {
        super(message);
    }
}
