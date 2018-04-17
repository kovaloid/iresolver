package com.koval.jresolver.report;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.Doc2vecClassifier;
import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.rules.RuleEngine;
import com.koval.jresolver.rules.RulesResult;

import java.util.List;

public class Launcher {

  private static JiraConnector jiraConnector;
  private static Doc2vecClassifier doc2vecClassifier;
  private static RuleEngine ruleEngine = new RuleEngine();

  public static void main(String[] args) throws Exception {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    jiraConnector = new JiraConnector(jiraProperties);
    doc2vecClassifier = new Doc2vecClassifier();
    configure();
    generate();
  }

  public static void configure() {
    doc2vecClassifier.configure();
  }

  public static void generate() throws Exception {
    List<Issue> actualIssues = jiraConnector.getActualIssues();
    System.out.println("Retrieving actual issues completed");
    TotalResults totalResults = new TotalResults();

    for (Issue actualIssue : actualIssues) {
      ClassifierResult classifierResult = doc2vecClassifier.execute(actualIssue);
      RulesResult ruleResult = ruleEngine.execute(actualIssue);
      totalResults.add(actualIssue, classifierResult, ruleResult);
    }
  }

}
