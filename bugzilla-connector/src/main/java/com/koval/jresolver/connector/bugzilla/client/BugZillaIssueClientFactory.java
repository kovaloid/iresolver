package com.koval.jresolver.connector.bugzilla.client;

import com.koval.jresolver.common.api.auth.Credentials;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueClientFactory;
import com.koval.jresolver.connector.bugzilla.exception.BugZillaConnectorException;


public class BugZillaIssueClientFactory implements IssueClientFactory {

  @Override
  public IssueClient getAnonymousClient(String host) throws BugZillaConnectorException {
    return null;
  }

  @Override
  public IssueClient getBasicClient(String host, Credentials credentials) throws BugZillaConnectorException {
    return null;
  }
}
