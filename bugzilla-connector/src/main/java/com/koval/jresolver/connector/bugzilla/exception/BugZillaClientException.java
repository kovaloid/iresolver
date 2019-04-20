package com.koval.jresolver.connector.bugzilla.exception;

import com.koval.jresolver.common.api.exception.ClientException;


public class BugZillaClientException extends ClientException {

  public BugZillaClientException(String message) {
    super(message);
  }

  public BugZillaClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
