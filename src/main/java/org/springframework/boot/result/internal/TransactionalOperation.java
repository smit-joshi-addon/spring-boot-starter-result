package org.springframework.boot.result.internal;

public interface TransactionalOperation {
    Boolean shouldRollback();
}