package com.revolut.moneytransfer.dagger.component;

import com.revolut.moneytransfer.dagger.module.TransactionModule;
import com.revolut.moneytransfer.dagger.module.TransactionModule_ProvideTransactionServiceFactory;
import com.revolut.moneytransfer.service.impl.TransactionServiceImpl;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerTransactionComponent implements TransactionComponent {
  private TransactionModule transactionModule;

  private DaggerTransactionComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static TransactionComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.transactionModule = builder.transactionModule;
  }

  @Override
  public TransactionServiceImpl buildTransactionService() {
    return TransactionModule_ProvideTransactionServiceFactory.proxyProvideTransactionService(
        transactionModule);
  }

  public static final class Builder {
    private TransactionModule transactionModule;

    private Builder() {}

    public TransactionComponent build() {
      if (transactionModule == null) {
        this.transactionModule = new TransactionModule();
      }
      return new DaggerTransactionComponent(this);
    }

    public Builder transactionModule(TransactionModule transactionModule) {
      this.transactionModule = Preconditions.checkNotNull(transactionModule);
      return this;
    }
  }
}
