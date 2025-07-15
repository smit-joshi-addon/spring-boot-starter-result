package org.springframework.boot.starter.result.domain.events;

import org.springframework.boot.starter.result.Result;
import org.springframework.context.ApplicationEvent;

public final class ResultEvent<T> extends ApplicationEvent {
    private final String eventName;
    private final Result<T> result;
    private final String methodName;
    private final Object[] args;

    public ResultEvent(Object source, String eventName, Result<T> result, String methodName, Object[] args) {
        super(source);
        this.eventName = eventName;
        this.result = result;
        this.methodName = methodName;
        this.args = args;
    }

    public String getEventName() {
        return eventName;
    }

    public Result<T> getResult() {
        return result;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public boolean isSuccess() {
        return result.isSuccess();
    }
}