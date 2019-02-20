package com.koval.jresolver.connector.jira.configuration.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;


public class CredentialsKeeper {

  private static final String CREDENTIALS_FILE_NAME = ".credentials";

  private final CredentialsProtector protector;
  private final File credentialsFile;

  public CredentialsKeeper(CredentialsProtector protector, ConnectorProperties properties) {
    this.protector = protector;
    this.credentialsFile = new File(properties.getCredentialsFolder(), CREDENTIALS_FILE_NAME);
  }

  public boolean isStored() {
    return credentialsFile.exists();
  }

  public void store(Credentials credentials) throws IOException {
    if (!isStored()) {
      credentialsFile.createNewFile();
    }
    try (PrintWriter fileWriter = new PrintWriter(credentialsFile, StandardCharsets.UTF_8.name())) {
      fileWriter.println(protector.encrypt(credentials.getUsername()));
      fileWriter.println(protector.encrypt(credentials.getPassword()));
    }
  }

  public Credentials load() throws IOException {
    try (InputStream inputStream = new FileInputStream(credentialsFile);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
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
