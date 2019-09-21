package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.repository.impl.UserRepositoryImpl;
import com.revolut.moneytransfer.service.impl.UserServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    UserRepository provideUserRepository() {
        return UserRepositoryImpl.getInstance();
    }

    @Provides
    UserServiceImpl provideUserService() {
        return new UserServiceImpl(this.provideUserRepository());
    }
}
