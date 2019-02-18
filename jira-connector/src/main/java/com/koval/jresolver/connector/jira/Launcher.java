package com.koval.jresolver.connector.jira;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.bean.JiraIssue;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    JiraConnector jiraConnector = new JiraConnector();
    if (args.length == 0) {
      LOGGER.warn("No arguments. Please use 'resolved' or 'unresolved'.");
    } else if (args.length == 1) {
      switch (args[0]) {
        case "resolved":
          LOGGER.info("Start creating data set with resolved issues...");
          jiraConnector.createResolvedDataSet();
          break;
        case "unresolved":
          LOGGER.info("Start getting unresolved issues...");
          List<JiraIssue> unresolvedIssues = jiraConnector.getUnresolvedIssues();
          unresolvedIssues.forEach((issue) -> LOGGER.info(issue.getKey()));
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'resolved' or 'unresolved'.");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'resolved' or 'unresolved'.");
    }
  }
}
