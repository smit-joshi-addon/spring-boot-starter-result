package org.springframework.boot.result.domain.errors;

public final class EntityAlreadyExistsError extends Error {
    public EntityAlreadyExistsError(String message) {
        super(message);
    }
}