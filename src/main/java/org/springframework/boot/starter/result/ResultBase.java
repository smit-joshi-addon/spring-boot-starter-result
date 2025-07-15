package org.springframework.boot.starter.result;

import org.springframework.boot.starter.result.domain.errors.Error;

sealed class ResultBase permits Result {
    private boolean success;
    private Error error;

    public ResultBase(boolean success, Error error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public Error getError() {
        return error;
    }
}