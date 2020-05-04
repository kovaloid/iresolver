package com.koval.resolver.common.api.component.connector;

import com.koval.resolver.common.api.exception.ConnectorException;


public interface IssueClientFactory {

  IssueClient getClient() throws ConnectorException;
}
