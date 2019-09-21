package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.repository.impl.TransactionRepositoryImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TransactionModule_ProvideTransactionRepositoryFactory
    implements Factory<TransactionRepositoryImpl> {
  private final TransactionModule module;

  public TransactionModule_ProvideTransactionRepositoryFactory(TransactionModule module) {
    this.module = module;
  }

  @Override
  public TransactionRepositoryImpl get() {
    return provideInstance(module);
  }

  public static TransactionRepositoryImpl provideInstance(TransactionModule module) {
    return proxyProvideTransactionRepository(module);
  }

  public static TransactionModule_ProvideTransactionRepositoryFactory create(
      TransactionModule module) {
    return new TransactionModule_ProvideTransactionRepositoryFactory(module);
  }

  public static TransactionRepositoryImpl proxyProvideTransactionRepository(
      TransactionModule instance) {
    return Preconditions.checkNotNull(
        instance.provideTransactionRepository(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
