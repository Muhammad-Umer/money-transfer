package com.revolut.moneytransfer.dagger.component;

import com.revolut.moneytransfer.dagger.module.AccountModule;
import com.revolut.moneytransfer.dagger.module.AccountModule_ProvideAccountServiceFactory;
import com.revolut.moneytransfer.service.impl.AccountServiceImpl;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAccountComponent implements AccountComponent {
  private AccountModule accountModule;

  private DaggerAccountComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AccountComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.accountModule = builder.accountModule;
  }

  @Override
  public AccountServiceImpl buildAccountService() {
    return AccountModule_ProvideAccountServiceFactory.proxyProvideAccountService(accountModule);
  }

  public static final class Builder {
    private AccountModule accountModule;

    private Builder() {}

    public AccountComponent build() {
      if (accountModule == null) {
        this.accountModule = new AccountModule();
      }
      return new DaggerAccountComponent(this);
    }

    public Builder accountModule(AccountModule accountModule) {
      this.accountModule = Preconditions.checkNotNull(accountModule);
      return this;
    }
  }
}
