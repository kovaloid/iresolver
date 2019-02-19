package com.koval.jresolver.connector.jira.core.processing.receive.impl;

import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;


public final class IssuesReceiverFactory {

  private IssuesReceiverFactory() {
  }

  public static BasicIssuesReceiver forResolvedIssues(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    return new BasicIssuesReceiver(jiraClient, connectorProperties, true);
  }

  public static BasicIssuesReceiver forUnresolvedIssues(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    return new BasicIssuesReceiver(jiraClient, connectorProperties, false);
  }
}
