package com.koval.resolver.connector.bugzilla.exception;

import com.koval.resolver.common.api.exception.ConnectorException;


public class BugzillaConnectorException extends ConnectorException {

  public BugzillaConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
}
