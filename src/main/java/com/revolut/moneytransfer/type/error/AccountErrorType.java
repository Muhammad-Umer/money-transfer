package com.revolut.moneytransfer.type.error;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

@AllArgsConstructor
public enum AccountErrorType implements ErrorEnumType<AccountErrorType> {

    ACCOUNT_NOT_CREATED(1, HttpStatus.INTERNAL_SERVER_ERROR_500, "Account cannot be created successfully"),
    ACCOUNT_NOT_UPDATED(2, HttpStatus.INTERNAL_SERVER_ERROR_500, "Account cannot be updated successfully"),
    CANNOT_FIND_ACCOUNT(3, HttpStatus.INTERNAL_SERVER_ERROR_500, "Account could not be retrieved"),
    INVALID_ACCOUNT(4, HttpStatus.INTERNAL_SERVER_ERROR_500, "Cannot find the account"),
    INVALID_ACCOUNT_ID(5, HttpStatus.BAD_REQUEST_400, "Invalid account id"),
    INVALID_ACCOUNT_TYPE(6, HttpStatus.BAD_REQUEST_400, "Invalid account type"),
    INSUFFICIENT_BALANCE(7, HttpStatus.BAD_REQUEST_400, "The balance for this operation is insufficient. Please deposit some amount first");

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
