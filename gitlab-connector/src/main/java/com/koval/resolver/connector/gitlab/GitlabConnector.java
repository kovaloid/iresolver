package com.koval.resolver.connector.gitlab;

import com.koval.resolver.common.api.component.connector.Connector;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.configuration.component.connectors.GitlabConnectorConfiguration;
import com.koval.resolver.connector.gitlab.core.GitlabIssueReceiver;

public class GitlabConnector implements Connector {

  private final IssueClient issueClient;
  private final GitlabConnectorConfiguration connectorProperties;

  public GitlabConnector(final IssueClient issueClient, final GitlabConnectorConfiguration connectorProperties) {
    this.issueClient = issueClient;
    this.connectorProperties = connectorProperties;
  }

  @Override
  public IssueReceiver getResolvedIssuesReceiver() {
    return new GitlabIssueReceiver(issueClient, connectorProperties, true);
  }

  @Override
  public IssueReceiver getUnresolvedIssuesReceiver() {
    return new GitlabIssueReceiver(issueClient, connectorProperties, false);
  }
}
