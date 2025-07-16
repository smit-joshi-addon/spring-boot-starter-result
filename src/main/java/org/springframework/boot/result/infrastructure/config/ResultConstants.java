package org.springframework.boot.result.infrastructure.config;

public interface ResultConstants {
    String getSuccessMessage();
    String getErrorMessage(String errorDetail);
}