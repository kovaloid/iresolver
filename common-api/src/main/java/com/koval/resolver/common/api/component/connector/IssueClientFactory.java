package com.koval.resolver.common.api.component.connector;

import com.koval.resolver.common.api.auth.Credentials;
import com.koval.resolver.common.api.exception.ConnectorException;


public interface IssueClientFactory {

  IssueClient getAnonymousClient(String host) throws ConnectorException;

  IssueClient getBasicClient(String host, Credentials credentials) throws ConnectorException;
}
