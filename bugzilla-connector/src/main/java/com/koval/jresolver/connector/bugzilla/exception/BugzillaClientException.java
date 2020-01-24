package com.koval.jresolver.connector.bugzilla.exception;

import com.koval.jresolver.common.api.exception.ClientException;


public class BugzillaClientException extends ClientException {

  public BugzillaClientException(String message) {
    super(message);
  }

  public BugzillaClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
