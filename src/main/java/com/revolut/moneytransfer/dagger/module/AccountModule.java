package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.repository.impl.AccountRepositoryImpl;
import com.revolut.moneytransfer.repository.impl.TransactionRepositoryImpl;
import com.revolut.moneytransfer.repository.impl.UserRepositoryImpl;
import com.revolut.moneytransfer.service.impl.AccountServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class AccountModule {

    @Provides
    AccountRepository provideAccountRepository(){
        return AccountRepositoryImpl.getInstance();
    }

    @Provides
    UserRepository provideUserRepository() {
        return UserRepositoryImpl.getInstance();
    }

    @Provides
    TransactionRepository provideTransactionRepository() {
        return TransactionRepositoryImpl.getInstance();
    }

    @Provides
    AccountServiceImpl provideAccountService(){
        return new AccountServiceImpl(
                this.provideAccountRepository(),
                this.provideUserRepository(),
                this.provideTransactionRepository()
        );
    }
}
