package com.koval.jresolver.report;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.Doc2vecClassifier;
import com.koval.jresolver.classifier.doc2vec.DocVectorizer;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.rules.RuleEngine;

import java.util.List;

public class Launcher {

  private static JiraConnector jiraConnector = new JiraConnector(null);
  private static Doc2vecClassifier doc2vecClassifier = new Doc2vecClassifier();
  private static RuleEngine ruleEngine = new RuleEngine();

  public static void main(String[] args) {
    setUp();
    use();
  }

  public static void setUp() {
    jiraConnector.createHistoryIssuesDataset("dataset.txt");
    doc2vecClassifier.train("dataset.txt");
  }

  public static void use() {
    List<Issue> actualIssues = jiraConnector.getActualIssues();
    TotalResults totalResults = new TotalResults();

    for (int i = 0; i < actualIssues.size(); i++) {
      ClassifierResult classifierResult = doc2vecClassifier.execute(actualIssues[i]);
      RuleResult ruleResult = ruleEngine.execute(actualIssues[i]);
      totalResults.add(actualIssues[i], classifierResult, ruleResult);
    }
  }

}
