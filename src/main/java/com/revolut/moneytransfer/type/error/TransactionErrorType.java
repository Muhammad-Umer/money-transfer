package com.revolut.moneytransfer.type.error;

import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

@AllArgsConstructor
public enum TransactionErrorType implements ErrorEnumType<TransactionErrorType>  {

    TRANSACTION_NOT_CREATED(1, HttpStatus.INTERNAL_SERVER_ERROR_500, "Transaction cannot be created successfully"),
    TRANSACTION_NOT_UPDATED(2, HttpStatus.INTERNAL_SERVER_ERROR_500, "Transaction cannot be updated successfully"),
    CANNOT_FIND_TRANSACTION(3, HttpStatus.INTERNAL_SERVER_ERROR_500, "Transaction could not be retrieved"),
    INVALID_TRANSACTION_ID(4, HttpStatus.BAD_REQUEST_400, "Invalid transaction id"),
    INVALID_TRANSACTION(5, HttpStatus.INTERNAL_SERVER_ERROR_500, "Cannot find the transaction");

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
