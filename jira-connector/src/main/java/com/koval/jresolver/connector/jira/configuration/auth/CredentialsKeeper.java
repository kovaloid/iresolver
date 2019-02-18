package com.koval.jresolver.connector.jira.configuration.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;

public class CredentialsKeeper {

  private static final String CREDENTIALS_FILE_NAME = ".credentials";

  private final CredentialsProtector protector;
  private final File credentialsFile;

  public CredentialsKeeper(CredentialsProtector protector, ConnectorProperties properties) {
    this.protector = protector;
    this.credentialsFile = new File(properties.getWorkFolder(), CREDENTIALS_FILE_NAME);
  }

  public boolean isStored() {
    return credentialsFile.exists();
  }

  public void store(Credentials credentials) throws IOException {
    if (!isStored()) {
      credentialsFile.createNewFile();
    }
    try (PrintWriter fileWriter = new PrintWriter(credentialsFile)) {
      fileWriter.println(protector.encrypt(credentials.getUsername()));
      fileWriter.println(protector.encrypt(credentials.getPassword()));
    }
  }

  public Credentials load() throws IOException {
    try (FileReader fileReader = new FileReader(credentialsFile);
         BufferedReader bufferedReader = new BufferedReader(fileReader)) {
      String username = protector.decrypt(bufferedReader.readLine());
      String password = protector.decrypt(bufferedReader.readLine());
      // if (encryptedUsername != null) {
      // }
      // if (encryptedPassword != null) {
      // }
      return new Credentials(username, password);
    }
  }

  public void remove() {
    credentialsFile.delete();
  }
}
