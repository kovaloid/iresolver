package com.koval.jresolver.connector.jira.core.processing;

import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;

public class IssuesReceiverFactory {

  public static IssuesReceiver forResolvedIssues(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    return new IssuesReceiver(jiraClient, connectorProperties, true);
  }

  public static IssuesReceiver forUnresolvedIssues(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    return new IssuesReceiver(jiraClient, connectorProperties, false);
  }
}
