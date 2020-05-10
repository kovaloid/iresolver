package com.koval.resolver.connector.jira.exception;

import com.koval.resolver.common.api.exception.ConnectorException;


public class JiraConnectorException extends ConnectorException {

  public JiraConnectorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
