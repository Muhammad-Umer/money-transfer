package com.revolut.moneytransfer.exception;

import com.revolut.moneytransfer.type.error.ErrorEnumType;

public class ServiceException extends BaseApplicationException {
    public ServiceException(ErrorEnumType<? extends Enum<?>> errorEnumType) {
        super(errorEnumType);
    }
}
