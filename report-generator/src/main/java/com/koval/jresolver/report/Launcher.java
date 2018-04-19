package com.koval.jresolver.report;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.classifier.impl.Doc2vecClassifier;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.rules.RuleEngine;
import com.koval.jresolver.rules.RulesResult;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static JiraConnector jiraConnector;
  private static Doc2vecClassifier doc2vecClassifier;
  private static RuleEngine ruleEngine;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {
    ruleEngine = new RuleEngine();
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    jiraConnector = new JiraConnector(jiraProperties);
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    doc2vecClassifier = new Doc2vecClassifier(classifierProperties);
    //configure();
    generate();
    ruleEngine.close();
  }

  private static void configure() throws IOException {
    doc2vecClassifier.configure();
  }

  private static void generate() throws Exception {
    List<JiraIssue> actualIssues = jiraConnector.getActualIssues();
    LOGGER.info("Retrieving actual issues completed");
    TotalResults totalResults = new TotalResults();

    for (JiraIssue actualIssue : actualIssues) {
      ClassifierResult classifierResult = doc2vecClassifier.execute(actualIssue);
      RulesResult ruleResult = ruleEngine.execute(actualIssue);
      totalResults.add(actualIssue, classifierResult, ruleResult);
    }
  }

}
