package org.springframework.boot.starter.result.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishEvent {
    EventType on() default EventType.SUCCESS;
    String eventName() default "";
    
    enum EventType {
        SUCCESS, FAILURE, BOTH
    }
}