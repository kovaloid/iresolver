package com.koval.resolver.exception;

public class IResolverException extends RuntimeException {

  public IResolverException(final String message) {
    super(message);
  }

  public IResolverException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
