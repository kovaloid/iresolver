package com.koval.resolver.common.api.auth;


import com.koval.resolver.common.api.util.CommandLineUtil;

public class Credentials {

  private final String username;
  private final String password;

  Credentials(final String username, final String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public static Credentials getCredentials(final String credentialsFolder) {
    final CredentialsProtector protector = new CredentialsProtector();
    final CredentialsKeeper keeper = new CredentialsKeeper(protector, credentialsFolder);
    Credentials credentials;
    if (keeper.isStored()) {
      credentials = keeper.load();
    } else {
      final String username = CommandLineUtil.getUsername();
      final String password = CommandLineUtil.getPassword();
      credentials = new Credentials(username, password);
      keeper.store(credentials);
    }
    return credentials;
  }
}
