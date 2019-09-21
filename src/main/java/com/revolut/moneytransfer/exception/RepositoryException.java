package com.revolut.moneytransfer.exception;

import com.revolut.moneytransfer.type.error.ErrorEnumType;

public class RepositoryException extends BaseApplicationException {
    public RepositoryException(ErrorEnumType<? extends Enum<?>> errorEnumType) {
        super(errorEnumType);
    }

    public RepositoryException(Exception e, ErrorEnumType<? extends Enum<?>> errorEnumType) {
        super(e, errorEnumType);
    }
}
