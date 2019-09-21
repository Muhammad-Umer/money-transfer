package com.revolut.moneytransfer.exception;

import com.revolut.moneytransfer.type.error.ErrorEnumType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseApplicationException extends RuntimeException {
    private int httpStatus;
    private String appCode;
    private String appMessage;


    protected BaseApplicationException(ErrorEnumType<? extends Enum<?>> errorEnumType) {
        this.setAppCode(errorEnumType.getAppCode());
        this.setAppMessage(errorEnumType.getAppMessage());
        this.setHttpStatus(errorEnumType.getHttpStatus());
    }

    protected BaseApplicationException(Exception e, ErrorEnumType<? extends Enum<?>> errorEnumType) {
        super(e);
        this.setAppCode(errorEnumType.getAppCode());
        this.setAppMessage(errorEnumType.getAppMessage());
        this.setHttpStatus(errorEnumType.getHttpStatus());
    }
}
