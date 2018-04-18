package com.koval.jresolver.report;


import java.util.List;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.classifier.impl.Doc2vecClassifier;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.rules.RuleEngine;
import com.koval.jresolver.rules.RulesResult;

public class HtmlReportGenerator implements ReportGenerator {

  @Override
  public void generate() throws Exception {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);
    Doc2vecClassifier doc2vecClassifier = new Doc2vecClassifier();
    RuleEngine ruleEngine = new RuleEngine();

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
