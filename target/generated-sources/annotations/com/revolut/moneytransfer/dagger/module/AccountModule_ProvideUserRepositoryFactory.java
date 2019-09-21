package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.UserRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AccountModule_ProvideUserRepositoryFactory implements Factory<UserRepository> {
  private final AccountModule module;

  public AccountModule_ProvideUserRepositoryFactory(AccountModule module) {
    this.module = module;
  }

  @Override
  public UserRepository get() {
    return provideInstance(module);
  }

  public static UserRepository provideInstance(AccountModule module) {
    return proxyProvideUserRepository(module);
  }

  public static AccountModule_ProvideUserRepositoryFactory create(AccountModule module) {
    return new AccountModule_ProvideUserRepositoryFactory(module);
  }

  public static UserRepository proxyProvideUserRepository(AccountModule instance) {
    return Preconditions.checkNotNull(
        instance.provideUserRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
