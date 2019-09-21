package com.revolut.moneytransfer.type.error;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorType implements ErrorEnumType<UserErrorType> {

    USER_NOT_CREATED(1,HttpStatus.INTERNAL_SERVER_ERROR_500, "User cannot be created successfully"),
    USER_NOT_UPDATED(2, HttpStatus.INTERNAL_SERVER_ERROR_500, "User cannot be updated successfully"),
    INVALID_USER_ID(3, HttpStatus.BAD_REQUEST_400, "Invalid user id"),
    INVALID_USER(4, HttpStatus.INTERNAL_SERVER_ERROR_500, "Cannot find the user"),
    INVALID_COUNTRY(5, HttpStatus.BAD_REQUEST_400, "The country provided is invalid");

    private final int code;
    private final int httpStatus;
    private final String message;

    @Override
    public String getAppCode() {
        return String.format("AE-%04d", this.code);
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
