package com.koval.jresolver.rules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);
    List<JiraIssue> issues = jiraConnector.getActualIssues();

    try (RuleEngine ruleEngine = new RuleEngine()) {
      issues.forEach((issue) -> {
        RulesResult result = ruleEngine.execute(issue);
        LOGGER.info("{} : {} ", issue.getKey(), result.toString());
      });
    } catch (Exception e) {
      LOGGER.error("Could not start rule engine", e);
    }
  }
}
