package com.revolut.moneytransfer.type.error;

import org.eclipse.jetty.http.HttpStatus;

public interface ErrorEnumType<E extends Enum<E>> {
    String getAppCode();

    String getAppMessage();

    default int getHttpStatus() {
        return HttpStatus.BAD_REQUEST_400;
    }
}
