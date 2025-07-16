package org.springframework.boot.result.infrastructure.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.boot.result.ResponseWrapper;
import org.springframework.boot.result.Result;
import org.springframework.boot.result.api.ResponseUtils;
import org.springframework.dao.DataIntegrityViolationException;

@ControllerAdvice
public final class GlobalResultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        Result<Void> result = Result.validationError(message);
        return ResponseUtils.asResponse(result);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleDataIntegrity(DataIntegrityViolationException e) {
        Result<Void> result = Result.entityAlreadyExistsError("Resource already exists");
        return ResponseUtils.asResponse(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>> handleGeneral(Exception e) {
        Result<Void> result = Result.failure(e.getMessage());
        return ResponseUtils.asResponse(result);
    }
}