package com.koval.resolver.common.api.auth;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.koval.resolver.common.api.exception.CredentialException;


public class CredentialsProtector {

  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private Cipher cipher;
  private SecretKey key;

  public CredentialsProtector() throws CredentialException {
    final String encryptionKey = "6veuxBtJA9IodvM9pLlWl0bffmWB8PWDBXGZaUAizkebHWvjSdv9sZBM1d3YweOj";
    final String encryptionSchema = "DESede";
    final byte[] arrayBytes = encryptionKey.getBytes(CHARSET);
    try {
      final KeySpec keySpec = new DESedeKeySpec(arrayBytes);
      final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptionSchema);
      cipher = Cipher.getInstance(encryptionSchema);
      key = secretKeyFactory.generateSecret(keySpec);
    } catch (GeneralSecurityException e) {
      throw new CredentialException("Could not initialize credential protector.", e);
    }
  }

  String encrypt(final String decryptedText) throws CredentialException {
    String encryptedString;
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      final byte[] plainText = decryptedText.getBytes(CHARSET);
      final byte[] encryptedText = cipher.doFinal(plainText);
      encryptedString = new String(Base64.encodeBase64(encryptedText), CHARSET);
    } catch (GeneralSecurityException e) {
      throw new CredentialException("Could not encrypt data: " + decryptedText, e);
    }
    return encryptedString;
  }


  String decrypt(final String encryptedString) throws CredentialException {
    String decryptedText;
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      final byte[] encryptedText = Base64.decodeBase64(encryptedString);
      final byte[] plainText = cipher.doFinal(encryptedText);
      decryptedText = new String(plainText, CHARSET);
    } catch (GeneralSecurityException e) {
      throw new CredentialException("Could not decrypt data: " + encryptedString, e);
    }
    return decryptedText;
  }
}
