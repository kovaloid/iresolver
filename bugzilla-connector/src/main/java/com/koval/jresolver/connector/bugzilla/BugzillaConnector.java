package com.koval.jresolver.connector.bugzilla;

import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.common.api.configuration.bean.connectors.BugzillaConnectorConfiguration;
import com.koval.jresolver.connector.bugzilla.core.BugzillaIssueReceiver;


public class BugzillaConnector implements Connector {

  private final IssueClient issueClient;
  private final BugzillaConnectorConfiguration connectorProperties;

  public BugzillaConnector(IssueClient issueClient, BugzillaConnectorConfiguration connectorProperties) {
    this.issueClient = issueClient;
    this.connectorProperties = connectorProperties;
  }

  @Override
  public IssueReceiver getResolvedIssuesReceiver() {
    return new BugzillaIssueReceiver(issueClient, connectorProperties, true);
  }

  @Override
  public IssueReceiver getUnresolvedIssuesReceiver() {
    return new BugzillaIssueReceiver(issueClient, connectorProperties, false);
  }
}
