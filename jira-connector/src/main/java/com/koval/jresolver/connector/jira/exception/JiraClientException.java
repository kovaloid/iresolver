package com.koval.jresolver.connector.jira.exception;

import com.koval.jresolver.common.api.exception.ClientException;


public class JiraClientException extends ClientException {

  public JiraClientException(String message) {
    super(message);
  }

  public JiraClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
