package com.revolut.moneytransfer.service.impl;

import com.revolut.moneytransfer.repository.UserRepository;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserServiceImpl_Factory implements Factory<UserServiceImpl> {
  private final Provider<UserRepository> userRepositoryProvider;

  public UserServiceImpl_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public UserServiceImpl get() {
    return provideInstance(userRepositoryProvider);
  }

  public static UserServiceImpl provideInstance(Provider<UserRepository> userRepositoryProvider) {
    return new UserServiceImpl(userRepositoryProvider.get());
  }

  public static UserServiceImpl_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new UserServiceImpl_Factory(userRepositoryProvider);
  }

  public static UserServiceImpl newUserServiceImpl(UserRepository userRepository) {
    return new UserServiceImpl(userRepository);
  }
}
