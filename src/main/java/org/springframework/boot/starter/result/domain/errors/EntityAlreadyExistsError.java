package org.springframework.boot.starter.result.domain.errors;

public final class EntityAlreadyExistsError extends Error {
    public EntityAlreadyExistsError(String message) {
        super(message);
    }
}