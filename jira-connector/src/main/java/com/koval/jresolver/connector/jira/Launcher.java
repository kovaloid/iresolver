package com.koval.jresolver.connector.jira;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsKeeper;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsProtector;
import com.koval.jresolver.connector.jira.core.JiraConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.bean.JiraIssue;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    ConnectorProperties connectorProperties = new ConnectorProperties();
    JiraClient jiraClient = new BasicJiraClient(connectorProperties.getUrl());
    JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
    if (args.length == 0) {
      LOGGER.warn("No arguments. Please use 'resolved' or 'unresolved'.");
    } else if (args.length == 1) {
      switch (args[0]) {
        case "resolved":
          LOGGER.info("Start creating data set with resolved issues...");
          jiraConnector.createResolvedDataSet();
          break;
        case "unresolved":
          LOGGER.info("Start getting unresolved issues...");
          Collection<Issue> unresolvedIssues = jiraConnector.getUnresolvedIssues();
          unresolvedIssues.forEach((issue) -> LOGGER.info(issue.getKey()));
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'resolved' or 'unresolved'.");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'resolved' or 'unresolved'.");
    }
  }
}
