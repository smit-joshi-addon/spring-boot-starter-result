package org.springframework.boot.starter.result.infrastructure.config;

public interface ResultConstants {
    String getSuccessMessage();
    String getErrorMessage(String errorDetail);
}