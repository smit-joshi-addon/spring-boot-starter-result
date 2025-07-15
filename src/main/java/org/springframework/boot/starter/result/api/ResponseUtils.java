package org.springframework.boot.starter.result.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.starter.result.ResponseWrapper;
import org.springframework.boot.starter.result.Result;
import org.springframework.boot.starter.result.domain.errors.EntityAlreadyExistsError;
import org.springframework.boot.starter.result.domain.errors.EntityNotFoundError;
import org.springframework.boot.starter.result.domain.errors.Error;
import org.springframework.boot.starter.result.domain.errors.UnauthorizedError;
import org.springframework.boot.starter.result.domain.errors.ValidationError;
import org.springframework.boot.starter.result.infrastructure.config.ResultConstantsProvider;

public final class ResponseUtils {

    public static <T> ResponseEntity<ResponseWrapper<T>> success(T data, String message, HttpStatus status) {
        return new ResponseEntity<>(ResponseWrapper.success(data, message), status);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> success(T data, HttpStatus status) {
        return new ResponseEntity<>(
                ResponseWrapper.success(data, ResultConstantsProvider.getResultConstants().getSuccessMessage()),
                status);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> success(T data) {
        return ResponseEntity
                .ok(ResponseWrapper.success(data, ResultConstantsProvider.getResultConstants().getSuccessMessage()));
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> failure(String message, HttpStatus status) {
        return new ResponseEntity<>(ResponseWrapper.failure(message), status);
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> failure(String message) {
        return ResponseEntity.badRequest().body(ResponseWrapper.failure(message));
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> failure() {
        return ResponseEntity.badRequest()
                .body(ResponseWrapper.failure(ResultConstantsProvider.getResultConstants().getSuccessMessage()));
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> asResponse(Result<T> result) {
        if (!result.isSuccess())
            return asError(result.getError());

        return ResponseEntity.ok(ResponseWrapper.success(result.getData(), result.getMessage()));
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> asError(Error exception) {
        return switch (exception) {
            case UnauthorizedError e ->
                new ResponseEntity<>(ResponseWrapper.failure(e.getMessage()), HttpStatus.UNAUTHORIZED);
            case EntityNotFoundError e ->
                new ResponseEntity<>(ResponseWrapper.failure(e.getMessage()), HttpStatus.NOT_FOUND);
            case EntityAlreadyExistsError e ->
                new ResponseEntity<>(ResponseWrapper.failure(e.getMessage()), HttpStatus.CONFLICT);
            case ValidationError e ->
                new ResponseEntity<>(ResponseWrapper.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(ResponseWrapper.failure(exception.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}