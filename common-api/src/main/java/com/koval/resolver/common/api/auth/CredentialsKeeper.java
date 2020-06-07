package com.koval.resolver.common.api.auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import com.koval.resolver.common.api.exception.CredentialException;


public class CredentialsKeeper {

  private static final String CREDENTIALS_FILE_NAME = ".credentials";

  private final CredentialsProtector protector;
  private final File credentialsFile;
  private final Charset charset = StandardCharsets.UTF_8;

  public CredentialsKeeper(final CredentialsProtector protector, final String credentialsPath) {
    this.protector = protector;
    this.credentialsFile = new File(credentialsPath, CREDENTIALS_FILE_NAME);
  }

  public boolean isStored() {
    return credentialsFile.exists();
  }

  public void store(final Credentials credentials) {
    if (!isStored()) {
      try {
        FileUtils.forceMkdir(credentialsFile.getParentFile());
        credentialsFile.createNewFile();
      } catch (IOException e) {
        throw new CredentialException("Could not create credentials file: " + credentialsFile.getAbsolutePath(), e);
      }
    }
    try (PrintWriter printWriter = new PrintWriter(credentialsFile, charset.name());
         BufferedWriter fileWriter = new BufferedWriter(printWriter)) {
      fileWriter.write(protector.encrypt(credentials.getUsername()));
      fileWriter.newLine();
      fileWriter.write(protector.encrypt(credentials.getPassword()));
      fileWriter.newLine();
    } catch (FileNotFoundException e) {
      throw new CredentialException("Could not find credentials file: " + credentialsFile.getAbsolutePath(), e);
    } catch (UnsupportedEncodingException e) {
      throw new CredentialException("Charset " + charset.name() + " does not supported.", e);
    } catch (IOException e) {
      throw new CredentialException("Could not write credentials to file " + credentialsFile.getAbsolutePath(), e);
    }
  }

  public Credentials load() {
    try (InputStream inputStream = new FileInputStream(credentialsFile);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      final String username = protector.decrypt(bufferedReader.readLine());
      final String password = protector.decrypt(bufferedReader.readLine());
      return new Credentials(username, password);
    } catch (FileNotFoundException e) {
      throw new CredentialException("Could not find credentials file: " + credentialsFile.getAbsolutePath(), e);
    } catch (IOException e) {
      throw new CredentialException("Could not load credentials from the file: " + credentialsFile.getAbsolutePath(), e);
    }
  }
}
