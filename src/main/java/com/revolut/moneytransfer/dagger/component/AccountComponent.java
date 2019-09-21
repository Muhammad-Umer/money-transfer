package com.revolut.moneytransfer.dagger.component;

import com.revolut.moneytransfer.dagger.module.AccountModule;
import com.revolut.moneytransfer.service.impl.AccountServiceImpl;
import dagger.Component;

@Component(modules = AccountModule.class)
public interface AccountComponent {
    AccountServiceImpl buildAccountService();
}
