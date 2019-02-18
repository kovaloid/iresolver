package com.koval.jresolver.report;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.connector.jira.core.JiraConnector;
import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
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
      ConnectorProperties connectorProperties = new ConnectorProperties("connector.properties");
      jiraConnector = new JiraConnector(connectorProperties);
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
    List<JiraIssue> actualIssues = jiraConnector.getUnresolvedIssues();
    LOGGER.info("Retrieving actual issues was completed.");
    reportGenerator.generate(actualIssues);
  }

}
