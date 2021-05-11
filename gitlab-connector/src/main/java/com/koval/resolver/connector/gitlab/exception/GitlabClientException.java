package com.koval.resolver.connector.gitlab.exception;

import com.koval.resolver.common.api.exception.ClientException;

public class GitlabClientException extends ClientException {

  public GitlabClientException(final String message) {
    super(message);
  }

  public GitlabClientException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
