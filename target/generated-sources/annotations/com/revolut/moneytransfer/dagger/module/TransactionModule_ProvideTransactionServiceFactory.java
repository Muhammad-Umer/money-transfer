package com.revolut.moneytransfer.dagger.module;

import com.revolut.moneytransfer.service.impl.TransactionServiceImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TransactionModule_ProvideTransactionServiceFactory
    implements Factory<TransactionServiceImpl> {
  private final TransactionModule module;

  public TransactionModule_ProvideTransactionServiceFactory(TransactionModule module) {
    this.module = module;
  }

  @Override
  public TransactionServiceImpl get() {
    return provideInstance(module);
  }

  public static TransactionServiceImpl provideInstance(TransactionModule module) {
    return proxyProvideTransactionService(module);
  }

  public static TransactionModule_ProvideTransactionServiceFactory create(
      TransactionModule module) {
    return new TransactionModule_ProvideTransactionServiceFactory(module);
  }

  public static TransactionServiceImpl proxyProvideTransactionService(TransactionModule instance) {
    return Preconditions.checkNotNull(
        instance.provideTransactionService(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
