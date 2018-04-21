package com.koval.jresolver.report;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static JiraConnector jiraConnector;
  private static DocClassifier docClassifier;
  private static ReportGenerator gen;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {

    try (RuleEngine ruleEngine = new DroolsRuleEngine()) {
      JiraProperties jiraProperties = new JiraProperties("connector.properties");
      jiraConnector = new JiraConnector(jiraProperties);
      ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
      docClassifier = new DocClassifier(classifierProperties);

      gen = new HtmlReportGenerator(docClassifier, ruleEngine);
      configure();
      generate();
    }
  }

  private static void configure() throws IOException {
    docClassifier.configure();
    gen.configure();
  }

  private static void generate() throws Exception {
    List<JiraIssue> actualIssues = jiraConnector.getActualIssues();
    LOGGER.info("Retrieving actual issues completed");


    gen.generate(actualIssues);
  }

}
