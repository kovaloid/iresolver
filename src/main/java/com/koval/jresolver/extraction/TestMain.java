package com.koval.jresolver.extraction;

import com.koval.jresolver.extraction.client.JiraClient;
import com.koval.jresolver.extraction.client.impl.BasicJiraClient;
import com.koval.jresolver.extraction.configuration.JiraExtractionProperties;
import com.koval.jresolver.extraction.deliver.DataConsumer;
import com.koval.jresolver.extraction.deliver.impl.BasicDataConsumer;
import com.koval.jresolver.extraction.issue.IssueHandler;
import com.koval.jresolver.extraction.issue.impl.BasicIssueHandler;
import com.koval.jresolver.extraction.process.DataRetriever;
import com.koval.jresolver.extraction.process.impl.BasicDataRetriever;


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
