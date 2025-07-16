package org.springframework.boot.result.domain.errors;

public final class ValidationError extends Error {
    
    public ValidationError(String message) {
        super(message);
    }
}