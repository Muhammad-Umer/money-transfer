package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.service.impl.UserServiceImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserModule_ProvideUserServiceFactory implements Factory<UserServiceImpl> {
  private final UserModule module;

  public UserModule_ProvideUserServiceFactory(UserModule module) {
    this.module = module;
  }

  @Override
  public UserServiceImpl get() {
    return provideInstance(module);
  }

  public static UserServiceImpl provideInstance(UserModule module) {
    return proxyProvideUserService(module);
  }

  public static UserModule_ProvideUserServiceFactory create(UserModule module) {
    return new UserModule_ProvideUserServiceFactory(module);
  }

  public static UserServiceImpl proxyProvideUserService(UserModule instance) {
    return Preconditions.checkNotNull(
        instance.provideUserService(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
