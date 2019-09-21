package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.repository.impl.AccountRepositoryImpl;
import com.revolut.moneytransfer.repository.impl.TransactionRepositoryImpl;
import com.revolut.moneytransfer.repository.impl.UserRepositoryImpl;
import com.revolut.moneytransfer.service.impl.ForexServiceImpl;
import com.revolut.moneytransfer.service.impl.TransactionServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class TransactionModule {

    @Provides
    TransactionRepositoryImpl provideTransactionRepository() {
        return TransactionRepositoryImpl.getInstance();
    }

    @Provides
    UserRepository provideUserRepository() {
        return UserRepositoryImpl.getInstance();
    }

    @Provides
    AccountRepository provideAccountRepository() {
        return AccountRepositoryImpl.getInstance();
    }

    @Provides
    TransactionServiceImpl provideTransactionService() {
        return new TransactionServiceImpl(
                this.provideTransactionRepository(),
                this.provideUserRepository(),
                new ForexServiceImpl(),
                this.provideAccountRepository()
        );
    }
}
