package com.koval.jresolver.connector.jira.configuration.auth;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.exception.JiraConnectorException;


public class CredentialsProtector {

  private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsProtector.class);
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private Cipher cipher;
  private SecretKey key;

  public CredentialsProtector() throws JiraConnectorException {
    String encryptionKey = "6veuxBtJA9IodvM9pLlWl0bffmWB8PWDBXGZaUAizkebHWvjSdv9sZBM1d3YweOj";
    String encryptionSchema = "DESede";
    byte[] arrayBytes = encryptionKey.getBytes(CHARSET);
    try {
      KeySpec keySpec = new DESedeKeySpec(arrayBytes);
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptionSchema);
      cipher = Cipher.getInstance(encryptionSchema);
      key = secretKeyFactory.generateSecret(keySpec);
    } catch (GeneralSecurityException e) {
      throw new JiraConnectorException("Could not initialize credential protector.", e);
    }
  }

  public String encrypt(String decryptedText) throws JiraConnectorException {
    LOGGER.info("Encryption performed");
    String encryptedString;
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] plainText = decryptedText.getBytes(CHARSET);
      byte[] encryptedText = cipher.doFinal(plainText);
      encryptedString = new String(Base64.encodeBase64(encryptedText), CHARSET);
    } catch (GeneralSecurityException e) {
      throw new JiraConnectorException("Could not encrypt data: " + decryptedText, e);
    }
    return encryptedString;
  }


  public String decrypt(String encryptedString) throws JiraConnectorException {
    LOGGER.info("Decryption performed");
    String decryptedText;
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] encryptedText = Base64.decodeBase64(encryptedString);
      byte[] plainText = cipher.doFinal(encryptedText);
      decryptedText = new String(plainText, CHARSET);
    } catch (GeneralSecurityException e) {
      throw new JiraConnectorException("Could not decrypt data: " + encryptedString, e);
    }
    return decryptedText;
  }
}
