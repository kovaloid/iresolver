package com.koval.jresolver.connector.jira.configuration.auth;

public class EncryptedCredentials {

  private String username;
  private String password;

  public EncryptedCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "EncryptedCredentials{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
