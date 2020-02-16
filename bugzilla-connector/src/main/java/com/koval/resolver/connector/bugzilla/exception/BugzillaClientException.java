package com.koval.resolver.connector.bugzilla.exception;

import com.koval.resolver.common.api.exception.ClientException;


public class BugzillaClientException extends ClientException {

  public BugzillaClientException(String message) {
    super(message);
  }

  public BugzillaClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
