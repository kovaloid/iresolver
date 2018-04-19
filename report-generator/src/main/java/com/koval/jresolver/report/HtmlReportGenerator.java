package com.koval.jresolver.report;

import java.util.List;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.classifier.impl.Doc2vecClassifier;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.rules.RuleEngine;
import com.koval.jresolver.rules.RulesResult;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

  @Override
  public void generate() throws Exception {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    Doc2vecClassifier doc2vecClassifier = new Doc2vecClassifier(classifierProperties);
    RuleEngine ruleEngine = new RuleEngine();

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
