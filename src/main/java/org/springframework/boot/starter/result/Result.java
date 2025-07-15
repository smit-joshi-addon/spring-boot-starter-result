package org.springframework.boot.starter.result;

import org.springframework.boot.starter.result.domain.errors.EntityAlreadyExistsError;
import org.springframework.boot.starter.result.domain.errors.EntityNotFoundError;
import org.springframework.boot.starter.result.domain.errors.Error;
import org.springframework.boot.starter.result.domain.errors.UnauthorizedError;
import org.springframework.boot.starter.result.domain.errors.ValidationError;
import org.springframework.boot.starter.result.infrastructure.config.ResultConstantsProvider;
import org.springframework.boot.starter.result.internal.TransactionalOperation;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.ArrayList;

public final class Result<T> extends ResultBase implements TransactionalOperation {
    private T data;
    private String message;

    public Result(T data) {
        super(true, null);
        this.data = data;
        this.message = ResultConstantsProvider.getResultConstants().getSuccessMessage();
    }

    public Result(T data, String message) {
        super(true, null);
        this.data = data;
        this.message = message;
    }

    public Result(boolean success) {
        super(success, null);
        this.data = null;
    }

    public Result(boolean success, Error error) {
        super(success, error);
        this.message = error.getMessage();
        this.data = null;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<T>(data, message);
    }

    public static <T> Result<T> failure(Error error) {
        return new Result<T>(false, error);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<T>(false, new Error(message));
    }

    public static <T> Result<T> failure(Boolean isError) {
        return new Result<T>(false);
    }

    public static <T> Result<T> unauthorizedError(String message) {
        return new Result<T>(false, new UnauthorizedError(message));
    }

    public static <T> Result<T> entityNotFoundError(String message) {
        return new Result<T>(false, new EntityNotFoundError(message));
    }

    public static <T> Result<T> entityAlreadyExistsError(String message) {
        return new Result<T>(false, new EntityAlreadyExistsError(message));
    }

    public static <T> Result<T> validationError(String message) {
        return new Result<T>(false, new ValidationError(message));
    }

    @Override
    public Boolean shouldRollback() {
        return !isSuccess();
    }

    public <R> Result<R> map(Function<T, R> mapper) {
        if (isSuccess() && data != null) {
            return Result.success(mapper.apply(data));
        }
        return new Result<>(isSuccess(), getError());
    }

    // Validation Chain
    public Result<T> validate(Predicate<T> predicate, String errorMessage) {
        if (!isSuccess()) {
            return this;
        }
        if (data != null && !predicate.test(data)) {
            return Result.validationError(errorMessage);
        }
        return this;
    }

    public Result<T> filter(Predicate<T> predicate, String errorMessage) {
        return validate(predicate, errorMessage);
    }

    // Async Support
    public static <T> CompletableFuture<Result<T>> async(java.util.function.Supplier<Result<T>> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    // Bulk Operations
    public static <T> Result<List<T>> combine(List<Result<T>> results) {
        List<T> successData = new ArrayList<>();
        for (Result<T> result : results) {
            if (!result.isSuccess()) {
                return new Result<>(false, result.getError());
            }
            successData.add(result.getData());
        }
        return Result.success(successData);
    }

    @SafeVarargs
    public static <T> Result<List<T>> combine(Result<T>... results) {
        return combine(List.of(results));
    }

    // Conditional Operations
    public Result<T> onSuccess(java.util.function.Consumer<T> action) {
        if (isSuccess() && data != null) {
            action.accept(data);
        }
        return this;
    }

    public Result<T> onFailure(java.util.function.Consumer<Error> action) {
        if (!isSuccess() && getError() != null) {
            action.accept(getError());
        }
        return this;
    }

    public <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
        if (isSuccess() && data != null) {
            return mapper.apply(data);
        }
        return new Result<>(isSuccess(), getError());
    }

    public Result<T> orElse(Result<T> alternative) {
        return isSuccess() ? this : alternative;
    }

    public T orElseGet(java.util.function.Supplier<T> supplier) {
        return isSuccess() ? data : supplier.get();
    }
}