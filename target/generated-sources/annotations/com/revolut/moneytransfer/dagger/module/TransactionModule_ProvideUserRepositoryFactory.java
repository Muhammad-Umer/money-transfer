package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.UserRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TransactionModule_ProvideUserRepositoryFactory
    implements Factory<UserRepository> {
  private final TransactionModule module;

  public TransactionModule_ProvideUserRepositoryFactory(TransactionModule module) {
    this.module = module;
  }

  @Override
  public UserRepository get() {
    return provideInstance(module);
  }

  public static UserRepository provideInstance(TransactionModule module) {
    return proxyProvideUserRepository(module);
  }

  public static TransactionModule_ProvideUserRepositoryFactory create(TransactionModule module) {
    return new TransactionModule_ProvideUserRepositoryFactory(module);
  }

  public static UserRepository proxyProvideUserRepository(TransactionModule instance) {
    return Preconditions.checkNotNull(
        instance.provideUserRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
