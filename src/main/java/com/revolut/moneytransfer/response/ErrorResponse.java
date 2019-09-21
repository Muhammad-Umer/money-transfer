package com.revolut.moneytransfer.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String appCode;
    private String appMessage;
}
