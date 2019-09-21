package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.UserRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserModule_ProvideUserRepositoryFactory implements Factory<UserRepository> {
  private final UserModule module;

  public UserModule_ProvideUserRepositoryFactory(UserModule module) {
    this.module = module;
  }

  @Override
  public UserRepository get() {
    return provideInstance(module);
  }

  public static UserRepository provideInstance(UserModule module) {
    return proxyProvideUserRepository(module);
  }

  public static UserModule_ProvideUserRepositoryFactory create(UserModule module) {
    return new UserModule_ProvideUserRepositoryFactory(module);
  }

  public static UserRepository proxyProvideUserRepository(UserModule instance) {
    return Preconditions.checkNotNull(
        instance.provideUserRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
