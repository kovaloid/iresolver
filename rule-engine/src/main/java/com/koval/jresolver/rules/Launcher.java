package com.koval.jresolver.rules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.JiraConnector;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;
import com.koval.jresolver.rules.results.RulesResult;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    ConnectorProperties connectorProperties = new ConnectorProperties();
    JiraClient jiraClient = new BasicJiraClient(connectorProperties.getUrl());
    JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
    Collection<Issue> issues = jiraConnector.getUnresolvedIssues();

    try (RuleEngine ruleEngine = new DroolsRuleEngine()) {
      issues.forEach((issue) -> {
        RulesResult result = ruleEngine.execute(issue);
        LOGGER.info("{} : {} ", issue.getKey(), result.toString());
      });
    } catch (Exception e) {
      LOGGER.error("Could not start rule engine.", e);
    }
  }
}
