package com.koval.jresolver.connector.bugzilla.exception;

import com.koval.jresolver.common.api.exception.ConnectorException;


public class BugzillaConnectorException extends ConnectorException {

  public BugzillaConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
}
