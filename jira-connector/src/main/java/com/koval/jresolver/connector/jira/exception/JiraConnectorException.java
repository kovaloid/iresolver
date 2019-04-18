package com.koval.jresolver.connector.jira.exception;

import com.koval.jresolver.common.api.exception.ConnectorException;


public class JiraConnectorException extends ConnectorException {

  public JiraConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
}
