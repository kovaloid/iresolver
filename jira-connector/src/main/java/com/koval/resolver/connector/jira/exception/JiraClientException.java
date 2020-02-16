package com.koval.resolver.connector.jira.exception;

import com.koval.resolver.common.api.exception.ClientException;


public class JiraClientException extends ClientException {

  public JiraClientException(String message) {
    super(message);
  }

  public JiraClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
