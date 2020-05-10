package com.koval.resolver.connector.bugzilla;

import com.koval.resolver.common.api.component.connector.Connector;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.configuration.bean.connectors.BugzillaConnectorConfiguration;
import com.koval.resolver.connector.bugzilla.core.BugzillaIssueReceiver;


public class BugzillaConnector implements Connector {

  private final IssueClient issueClient;
  private final BugzillaConnectorConfiguration connectorProperties;

  public BugzillaConnector(final IssueClient issueClient, final BugzillaConnectorConfiguration connectorProperties) {
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
