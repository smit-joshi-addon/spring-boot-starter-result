package org.springframework.boot.starter.result.infrastructure.config;

import java.util.ServiceLoader;

public final class ResultConstantsProvider {
    private static ResultConstants INSTANCE;

    static {
        INSTANCE = loadResultConstants();
    }

    // Private constructor to prevent instantiation
    private ResultConstantsProvider() {
    }

    // Load ResultConstants from Spring if available, else fallback to ServiceLoader
    private static ResultConstants loadResultConstants() {
        // First, try loading via Spring if running in a Spring Boot app
        ResultConstants springBean = getSpringBeanIfAvailable();
        if (springBean != null) {
            return springBean;
        }

        // Otherwise, fallback to normal ServiceLoader behavior
        ServiceLoader<ResultConstants> loader = ServiceLoader.load(ResultConstants.class);
        return loader.findFirst().orElse(new DefaultResultConstants());
    }

    // Get instance method for the ResultConstants
    public static ResultConstants getResultConstants() {
        return INSTANCE;
    }

    // Method to set the instance manually
    public static void setResultConstants(ResultConstants constants) {
        if (constants == null) {
            throw new IllegalArgumentException("ResultConstants instance cannot be null");
        }
        INSTANCE = constants;
    }

    // Attempt to get Spring bean if Spring context is available
    private static ResultConstants getSpringBeanIfAvailable() {
        try {
            // Check if Spring is available by looking for the ApplicationContext class
            Class<?> springContextClass = Class.forName("org.springframework.context.ApplicationContext");
            Object context = springContextClass.getMethod("getBean", String.class).invoke(null, "resultConstants");

            if (context != null) {
                return (ResultConstants) context;
            }
        } catch (Exception e) {
            // Ignore exceptions if Spring context isn't available
        }
        return null;
    }
}