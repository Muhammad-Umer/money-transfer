package com.revolut.moneytransfer.type.application;

import java.util.HashMap;

public enum AccountType {
    CURRENT(1),
    SAVINGS(2);

    private static HashMap<Integer, AccountType> accountTypes = new HashMap<>(AccountType.values().length);

    static {
        for (AccountType accountType : AccountType.values()) {
            accountTypes.put(accountType.getCode(), accountType);
        }
    }

    public static AccountType getAccountType(Integer value) {
        return accountTypes.get(value);
    }

    int code;

    AccountType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
