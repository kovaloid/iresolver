package com.koval.jresolver.connector;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.connector.configuration.JiraProperties;

import java.util.List;


public class TestMain {
  public static void main(String[] args) throws Exception {
    /*JiraClient jiraClient = new BasicJiraClient("https://issues.apache.org/jira");

    JiraExtractionProperties jiraExtractionProperties = new JiraExtractionProperties()
      .searchJqlRequest("project = AMQ")
      .maxResults(2)
      .startAt(0)
      .delayAfterEveryRequest(3000);

    IssueHandler issueHandler = new BasicIssueHandler();
    DataConsumer dataConsumer = new BasicDataConsumer();

    DataRetriever dataRetriever = new BasicDataRetriever(jiraClient, dataConsumer, jiraExtractionProperties);


    new Thread(() -> {
      dataRetriever.start();
    }).start();

    while (true) {
      System.out.println(dataRetriever.getStatus());
      Thread.sleep(2000);
    }*/


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
