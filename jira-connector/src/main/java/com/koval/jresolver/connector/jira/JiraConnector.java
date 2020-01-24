package com.koval.jresolver.connector.jira;

import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.common.api.configuration.bean.connectors.JiraConnectorConfiguration;
import com.koval.jresolver.connector.jira.core.JiraIssueReceiver;


public class JiraConnector implements Connector {

  private final IssueClient issueClient;
  private final JiraConnectorConfiguration connectorProperties;

  public JiraConnector(IssueClient issueClient, JiraConnectorConfiguration connectorProperties) {
    this.issueClient = issueClient;
    this.connectorProperties = connectorProperties;
  }

  @Override
  public IssueReceiver getResolvedIssuesReceiver() {
    return new JiraIssueReceiver(issueClient, connectorProperties, true);
  }

  @Override
  public IssueReceiver getUnresolvedIssuesReceiver() {
    return new JiraIssueReceiver(issueClient, connectorProperties, false);
  }
}
