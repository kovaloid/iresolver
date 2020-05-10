package com.koval.resolver.connector.bugzilla.exception;

import com.koval.resolver.common.api.exception.ClientException;


public class BugzillaClientException extends ClientException {

  public BugzillaClientException(final String message) {
    super(message);
  }

  public BugzillaClientException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
