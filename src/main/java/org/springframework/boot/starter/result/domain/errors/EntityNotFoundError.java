package org.springframework.boot.starter.result.domain.errors;

public final class EntityNotFoundError extends Error {
    public EntityNotFoundError(String message) {
        super(message);
    }
}