package com.revolut.moneytransfer.dagger.component;

import com.revolut.moneytransfer.dagger.module.UserModule;
import com.revolut.moneytransfer.service.impl.UserServiceImpl;
import dagger.Component;

@Component(modules = UserModule.class)
public interface UserComponent {
    UserServiceImpl buildUserService();
}
