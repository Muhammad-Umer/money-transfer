package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.service.impl.AccountServiceImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AccountModule_ProvideAccountServiceFactory
    implements Factory<AccountServiceImpl> {
  private final AccountModule module;

  public AccountModule_ProvideAccountServiceFactory(AccountModule module) {
    this.module = module;
  }

  @Override
  public AccountServiceImpl get() {
    return provideInstance(module);
  }

  public static AccountServiceImpl provideInstance(AccountModule module) {
    return proxyProvideAccountService(module);
  }

  public static AccountModule_ProvideAccountServiceFactory create(AccountModule module) {
    return new AccountModule_ProvideAccountServiceFactory(module);
  }

  public static AccountServiceImpl proxyProvideAccountService(AccountModule instance) {
    return Preconditions.checkNotNull(
        instance.provideAccountService(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
