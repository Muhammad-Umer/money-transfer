package com.revolut.moneytransfer.repository;

import com.revolut.moneytransfer.dto.Account;

import java.util.List;

public interface AccountRepository extends GenericRepository<Account> {
    List<Account> findByUser(Integer userId);

    List<Account> findByUserAndAccountType(Integer userId,  Integer accountType);
}
