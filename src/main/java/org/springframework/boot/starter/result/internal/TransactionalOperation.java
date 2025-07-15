package org.springframework.boot.starter.result.internal;

public interface TransactionalOperation {
    Boolean shouldRollback();
}