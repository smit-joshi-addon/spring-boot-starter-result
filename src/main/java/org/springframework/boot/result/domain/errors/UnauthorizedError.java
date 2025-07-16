package org.springframework.boot.result.domain.errors;

public final class UnauthorizedError extends Error {
    public UnauthorizedError(String message) {
        super(message);
    }
}