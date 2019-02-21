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


public class CredentialsProtector {

  private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsProtector.class);
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private final Cipher cipher;
  private final SecretKey key;

  public CredentialsProtector() throws GeneralSecurityException {
    String encryptionKey = "6veuxBtJA9IodvM9pLlWl0bffmWB8PWDBXGZaUAizkebHWvjSdv9sZBM1d3YweOj";
    String encryptionSchema = "DESede";
    byte[] arrayBytes = encryptionKey.getBytes(CHARSET);
    KeySpec keySpec = new DESedeKeySpec(arrayBytes);
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptionSchema);
    cipher = Cipher.getInstance(encryptionSchema);
    key = secretKeyFactory.generateSecret(keySpec);
  }

  public String encrypt(String decryptedText) {
    LOGGER.info("Encryption performed");
    String encryptedString = null;
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] plainText = decryptedText.getBytes(CHARSET);
      byte[] encryptedText = cipher.doFinal(plainText);
      encryptedString = new String(Base64.encodeBase64(encryptedText), CHARSET);
    } catch (Exception e) {
      LOGGER.error("Could not encrypt", e);
    }
    return encryptedString;
  }


  public String decrypt(String encryptedString) {
    LOGGER.info("Decryption performed");
    String decryptedText = null;
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] encryptedText = Base64.decodeBase64(encryptedString);
      byte[] plainText = cipher.doFinal(encryptedText);
      decryptedText = new String(plainText, CHARSET);
    } catch (Exception e) {
      LOGGER.error("Could not decrypt", e);
    }
    return decryptedText;
  }
}
