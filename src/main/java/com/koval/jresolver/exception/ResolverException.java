package com.koval.jresolver.exception;

public class ResolverException extends RuntimeException {

  public ResolverException(String message) {
    super(message);
  }

  public ResolverException(String message, Throwable cause) {
    super(message, cause);
  }
}
