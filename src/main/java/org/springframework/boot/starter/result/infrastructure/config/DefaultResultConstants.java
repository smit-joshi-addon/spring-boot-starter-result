package org.springframework.boot.starter.result.infrastructure.config;

public final class DefaultResultConstants implements ResultConstants {

    @Override
    public String getSuccessMessage() {
        return "Operation completed successfully.";
    }

    @Override
    public String getErrorMessage(String errorDetail) {
        return "An error occurred: "+errorDetail;
    }

}