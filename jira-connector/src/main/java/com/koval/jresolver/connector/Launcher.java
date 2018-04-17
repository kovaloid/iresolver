package com.koval.jresolver.connector;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.connector.configuration.JiraProperties;

import java.util.List;


public class Launcher {
  public static void main(String[] args) throws Exception {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);

    /*new Thread(() -> {
      jiraConnector.createHistoryIssuesDataset("dataset.txt");
    }).start();

    while (true) {
      System.out.println(jiraConnector.getStatus());
      Thread.sleep(2000);
    }*/

    List<Issue> actualIssues = jiraConnector.getActualIssues();
  }
}
