package com.koval.jresolver.connector.jira;

import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.connector.jira.core.impl.IssuesReceiverImpl;


public class JiraConnector {

  private final JiraClient jiraClient;
  private final ConnectorProperties connectorProperties;

  public JiraConnector(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    this.jiraClient = jiraClient;
    this.connectorProperties = connectorProperties;
  }

  public IssuesReceiver getResolvedIssuesReceiver() {
    return new IssuesReceiverImpl(jiraClient, connectorProperties, true);
  }

  public IssuesReceiver getUnresolvedIssuesReceiver() {
    return new IssuesReceiverImpl(jiraClient, connectorProperties, false);
  }
}