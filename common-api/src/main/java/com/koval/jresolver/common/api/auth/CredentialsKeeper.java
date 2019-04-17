package com.koval.jresolver.common.api.auth;

import com.koval.jresolver.common.api.exception.ConnectorException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


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

  public void store(Credentials credentials) throws ConnectorException {
    if (!isStored()) {
      try {
        FileUtils.forceMkdir(credentialsFile.getParentFile());
        credentialsFile.createNewFile();
      } catch (IOException e) {
        throw new ConnectorException("Could not create credentials file: " + credentialsFile.getAbsolutePath(), e);
      }
    }
    try (PrintWriter fileWriter = new PrintWriter(credentialsFile, charset.name())) {
      fileWriter.println(protector.encrypt(credentials.getUsername()));
      fileWriter.println(protector.encrypt(credentials.getPassword()));
    } catch (FileNotFoundException e) {
      throw new ConnectorException("Could not find credentials file: " + credentialsFile.getAbsolutePath(), e);
    } catch (UnsupportedEncodingException e) {
      throw new ConnectorException("Charset " + charset.name() + " does not supported.", e);
    }
  }

  public Credentials load() throws ConnectorException {
    try (InputStream inputStream = new FileInputStream(credentialsFile);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      String username = protector.decrypt(bufferedReader.readLine());
      String password = protector.decrypt(bufferedReader.readLine());
      return new Credentials(username, password);
    } catch (FileNotFoundException e) {
      throw new ConnectorException("Could not find credentials file: " + credentialsFile.getAbsolutePath(), e);
    } catch (IOException e) {
      throw new ConnectorException("Could not load credentials from the file: " + credentialsFile.getAbsolutePath(), e);
    }
  }
}
