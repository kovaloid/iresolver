package com.koval.jresolver.report;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.JiraConnector;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static JiraConnector jiraConnector;
  private static Classifier classifier;
  private static ReportGenerator reportGenerator;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {
    try (RuleEngine ruleEngine = new DroolsRuleEngine()) {
      ConnectorProperties connectorProperties = new ConnectorProperties();
      JiraClient jiraClient = new BasicJiraClient(connectorProperties.getUrl());
      jiraConnector = new JiraConnector(jiraClient, connectorProperties);
      ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
      classifier = new DocClassifier(classifierProperties);
      reportGenerator = new HtmlReportGenerator(classifier, ruleEngine);
      configure();
      generate();
    }
  }

  private static void configure() throws IOException {
    classifier.configure();
    reportGenerator.configure();
  }

  private static void generate() throws Exception {
    Collection<Issue> actualIssues = jiraConnector.getUnresolvedIssues();
    LOGGER.info("Retrieving actual issues was completed.");
    reportGenerator.generate(actualIssues);
  }

}
