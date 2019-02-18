package com.koval.jresolver.connector.jira.configuration.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class CredentialsKeeper {

  private static final String CREDENTIALS_FILE_NAME = ".credentials";

  private File credentialsFile = new File("C:/jresolver/data/", CREDENTIALS_FILE_NAME);

  public CredentialsKeeper() {
  }

  public boolean isStored() {
    return credentialsFile.exists();
  }

  public void store(EncryptedCredentials encryptedCredentials) throws IOException {
    if (!isStored()) {
      credentialsFile.createNewFile();
    }
    try (PrintWriter fileWriter = new PrintWriter(credentialsFile)) {
      fileWriter.println(encryptedCredentials.getUsername());
      fileWriter.println(encryptedCredentials.getPassword());
    }
  }

  public EncryptedCredentials load() throws IOException {
    /*Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_NAME)) {
      properties.load(input);
      String username = properties.getProperty("username");
      String password = properties.getProperty("password");
      return new EncryptedCredentials(username, password);
    }*/

    try (FileReader fileReader = new FileReader(credentialsFile);
         BufferedReader bufferedReader = new BufferedReader(fileReader)) {
      String encryptedUsername = bufferedReader.readLine();
      String encryptedPassword = bufferedReader.readLine();
      // if (encryptedUsername != null) {
      // }
      // if (encryptedPassword != null) {
      // }
      return new EncryptedCredentials(encryptedUsername, encryptedPassword);
    }
  }

  public void remove() {
    credentialsFile.delete();
  }


  public static void main(String[] args) throws IOException {
    CredentialsKeeper k = new CredentialsKeeper();
    EncryptedCredentials c = k.load();
    System.out.println(c);
  }

}
