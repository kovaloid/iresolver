package com.koval.resolver.connector.jira.exception;

import com.koval.resolver.common.api.exception.ClientException;


public class JiraClientException extends ClientException {

  public JiraClientException(final String message) {
    super(message);
  }

  public JiraClientException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
