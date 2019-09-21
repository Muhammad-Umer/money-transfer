package com.revolut.moneytransfer.dagger.component;

import com.revolut.moneytransfer.dagger.module.TransactionModule;
import com.revolut.moneytransfer.service.impl.TransactionServiceImpl;
import dagger.Component;

@Component(modules = TransactionModule.class)
public interface TransactionComponent {
    TransactionServiceImpl buildTransactionService();
}
