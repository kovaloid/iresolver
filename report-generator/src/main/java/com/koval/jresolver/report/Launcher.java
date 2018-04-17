package com.koval.jresolver.report;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.Doc2vecClassifier;
import com.koval.jresolver.classifier.doc2vec.DocVectorizer;
import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.rules.RuleEngine;
import com.koval.jresolver.rules.RulesResult;

import java.util.List;

public class Launcher {

  private static JiraConnector jiraConnector = new JiraConnector(null);
  private static Doc2vecClassifier doc2vecClassifier = new Doc2vecClassifier();
  private static RuleEngine ruleEngine = new RuleEngine();

  public static void main(String[] args) {
    configure();
    generate();
  }

  public static void configure() {
    doc2vecClassifier.configure();
  }

  public static void generate() {
    List<Issue> actualIssues = jiraConnector.getActualIssues();
    TotalResults totalResults = new TotalResults();

    for (int i = 0; i < actualIssues.size(); i++) {
      ClassifierResult classifierResult = doc2vecClassifier.execute(actualIssues.get(i));
      RulesResult ruleResult = ruleEngine.execute(actualIssues.get(i));
      totalResults.add(actualIssues.get(i), classifierResult, ruleResult);
    }
  }

}
