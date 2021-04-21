package com.koval.resolver.connector.gitlab.exception;

import com.koval.resolver.common.api.exception.ConnectorException;


public class GitlabConnectorException extends ConnectorException {

  public GitlabConnectorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
