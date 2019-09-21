package com.revolut.moneytransfer.dagger.component;

import com.revolut.moneytransfer.dagger.module.UserModule;
import com.revolut.moneytransfer.dagger.module.UserModule_ProvideUserServiceFactory;
import com.revolut.moneytransfer.service.impl.UserServiceImpl;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerUserComponent implements UserComponent {
  private UserModule userModule;

  private DaggerUserComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static UserComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.userModule = builder.userModule;
  }

  @Override
  public UserServiceImpl buildUserService() {
    return UserModule_ProvideUserServiceFactory.proxyProvideUserService(userModule);
  }

  public static final class Builder {
    private UserModule userModule;

    private Builder() {}

    public UserComponent build() {
      if (userModule == null) {
        this.userModule = new UserModule();
      }
      return new DaggerUserComponent(this);
    }

    public Builder userModule(UserModule userModule) {
      this.userModule = Preconditions.checkNotNull(userModule);
      return this;
    }
  }
}
