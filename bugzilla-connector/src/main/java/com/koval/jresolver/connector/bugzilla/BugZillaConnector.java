package com.koval.jresolver.connector.bugzilla;

import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.connector.bugzilla.configuration.BugZillaConnectorProperties;
import com.koval.jresolver.connector.bugzilla.core.BugZillaIssueReceiver;


public class BugZillaConnector implements Connector {

  private final IssueClient issueClient;
  private final BugZillaConnectorProperties connectorProperties;

  public BugZillaConnector(IssueClient issueClient, BugZillaConnectorProperties connectorProperties) {
    this.issueClient = issueClient;
    this.connectorProperties = connectorProperties;
  }

  @Override
  public IssueReceiver getResolvedIssuesReceiver() {
    return new BugZillaIssueReceiver(issueClient, connectorProperties, true);
  }

  @Override
  public IssueReceiver getUnresolvedIssuesReceiver() {
    return new BugZillaIssueReceiver(issueClient, connectorProperties, false);
  }
}
