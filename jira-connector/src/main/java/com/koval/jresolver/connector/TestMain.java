package com.koval.jresolver.connector;

import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraExtractionProperties;
import com.koval.jresolver.connector.deliver.DataConsumer;
import com.koval.jresolver.connector.deliver.impl.BasicDataConsumer;
import com.koval.jresolver.connector.issue.IssueHandler;
import com.koval.jresolver.connector.issue.impl.BasicIssueHandler;
import com.koval.jresolver.connector.process.DataRetriever;
import com.koval.jresolver.connector.process.impl.BasicDataRetriever;


public class TestMain {
  public static void main(String[] args) throws Exception {
    JiraClient jiraClient = new BasicJiraClient("https://issues.apache.org/jira");

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
    }

  }
}
