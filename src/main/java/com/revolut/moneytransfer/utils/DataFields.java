package com.revolut.moneytransfer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataFields {
    
    public static final String ID = "id";
    public static final String CREATION_DATE = "creation_date";
    public static final String UPDATE_DATE = "update_date";


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class AccountFields {
        public static final String USER_ID = "user_id";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String ACCOUNT_BALANCE = "account_balance";
        public static final String OPEN = "open";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class TransactionFields {
        public static final String FROM_ACCOUNT = "from_account";
        public static final String TO_ACCOUNT = "to_account";
        public static final String AMOUNT = "amount";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserFields {
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL_ADDRESS = "email_address";
        public static final String COUNTRY = "country";
        public static final String CURRENCY = "currency";
    }
}
