package com.revolut.moneytransfer.type.error;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

@AllArgsConstructor
public enum GenericErrorType implements ErrorEnumType<GenericErrorType> {

    SOMETHING_WENT_WRONG(1, HttpStatus.INTERNAL_SERVER_ERROR_500, "Something went wrong");

    private final int code;
    private final int httpStatus;
    private final String message;

    @Override
    public String getAppCode() {
        return String.format("GE-%04d", this.code);
    }

    @Override
    public String getAppMessage() {
        return this.message;
    }

    @Override
    public int getHttpStatus() {
        return this.httpStatus;
    }
}
