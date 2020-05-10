package com.koval.resolver.common.api.exception;


public class ClientException extends RuntimeException {

  public ClientException(final String message) {
    super(message);
  }

  public ClientException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
