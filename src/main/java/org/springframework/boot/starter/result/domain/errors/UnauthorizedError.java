package org.springframework.boot.starter.result.domain.errors;

public final class UnauthorizedError extends Error {
    public UnauthorizedError(String message) {
        super(message);
    }
}