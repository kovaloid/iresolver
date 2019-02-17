package com.koval.jresolver.connector2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.koval.jresolver.connector2.bean.JiraIssue;
import com.koval.jresolver.connector2.configuration.JiraProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);

    if (args.length == 0) {
      LOGGER.warn("No arguments. Please use 'history' or 'actual'");
    } else if (args.length == 1) {
      switch (args[0]) {
        case "resolved":
          LOGGER.info("Get history issues...");
          jiraConnector.createHistoryIssuesDataSet("DataSet.txt");
          break;
        case "unresolved":
          LOGGER.info("Get actual issues...");
          List<JiraIssue> actualIssues = jiraConnector.getActualIssues();
          actualIssues.forEach((issue) -> LOGGER.info(issue.getKey()));
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'history' or 'actual'");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'history' or 'actual'");
    }
  }
}
