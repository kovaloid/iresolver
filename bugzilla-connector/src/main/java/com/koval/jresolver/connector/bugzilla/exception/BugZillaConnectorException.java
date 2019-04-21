package com.koval.jresolver.connector.bugzilla.exception;

import com.koval.jresolver.common.api.exception.ConnectorException;


public class BugZillaConnectorException extends ConnectorException {

  public BugZillaConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
}
