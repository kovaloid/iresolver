package com.koval.jresolver.connector.jira;

import java.io.Closeable;
import java.io.IOException;

import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.connector.jira.core.impl.BasicIssuesReceiver;


public class JiraConnector implements Closeable {

  private final JiraClient jiraClient;
  private final ConnectorProperties connectorProperties;

  public JiraConnector(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    this.jiraClient = jiraClient;
    this.connectorProperties = connectorProperties;
  }

  public IssuesReceiver getResolvedIssuesReceiver() {
    return new BasicIssuesReceiver(jiraClient, connectorProperties, true);
  }

  public IssuesReceiver getUnresolvedIssuesReceiver() {
    return new BasicIssuesReceiver(jiraClient, connectorProperties, false);
  }

  @Override
  public void close() throws IOException {
    jiraClient.close();
  }
}
