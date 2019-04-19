package com.koval.jresolver.common.api.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.koval.jresolver.common.api.exception.CredentialException;


public class CredentialsKeeper {

  private static final String CREDENTIALS_FILE_NAME = ".credentials";

  private final CredentialsProtector protector;
  private final File credentialsFile;
  private final Charset charset = StandardCharsets.UTF_8;

  public CredentialsKeeper(CredentialsProtector protector, String credentialsPath) {
    this.protector = protector;
    this.credentialsFile = new File(credentialsPath, CREDENTIALS_FILE_NAME);
  }

  public boolean isStored() {
    return credentialsFile.exists();
  }

  public void store(Credentials credentials) {
    if (!isStored()) {
      try {
        FileUtils.forceMkdir(credentialsFile.getParentFile());
        credentialsFile.createNewFile();
      } catch (IOException e) {
        throw new CredentialException("Could not create credentials file: " + credentialsFile.getAbsolutePath(), e);
      }
    }
    try (PrintWriter fileWriter = new PrintWriter(credentialsFile, charset.name())) {
      fileWriter.println(protector.encrypt(credentials.getUsername()));
      fileWriter.println(protector.encrypt(credentials.getPassword()));
    } catch (FileNotFoundException e) {
      throw new CredentialException("Could not find credentials file: " + credentialsFile.getAbsolutePath(), e);
    } catch (UnsupportedEncodingException e) {
      throw new CredentialException("Charset " + charset.name() + " does not supported.", e);
    }
  }

  public Credentials load() {
    try (InputStream inputStream = new FileInputStream(credentialsFile);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      String username = protector.decrypt(bufferedReader.readLine());
      String password = protector.decrypt(bufferedReader.readLine());
      return new Credentials(username, password);
    } catch (FileNotFoundException e) {
      throw new CredentialException("Could not find credentials file: " + credentialsFile.getAbsolutePath(), e);
    } catch (IOException e) {
      throw new CredentialException("Could not load credentials from the file: " + credentialsFile.getAbsolutePath(), e);
    }
  }
}
