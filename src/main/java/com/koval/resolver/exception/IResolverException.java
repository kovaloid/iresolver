package com.koval.resolver.exception;

public class IResolverException extends RuntimeException {

  public IResolverException(String message) {
    super(message);
  }

  public IResolverException(String message, Throwable cause) {
    super(message, cause);
  }
}
