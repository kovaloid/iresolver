package com.koval.jresolver.connector;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.connector.deliver.DataConsumer;
import com.koval.jresolver.connector.deliver.impl.FileDataConsumer;
import com.koval.jresolver.connector.deliver.impl.ListDataConsumer;
import com.koval.jresolver.connector.process.DataRetriever;
import com.koval.jresolver.connector.process.impl.BasicDataRetriever;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class JiraConnector {

  private JiraClient jiraClient;
  private DataRetriever dataRetriever;
  private String historyJql;
  private String actualJql;
  private int maxResults;
  private int startAt;
  private int delayAfterEveryRequest;

  public JiraConnector(JiraProperties jiraProperties) throws URISyntaxException {
    this.jiraClient = new BasicJiraClient(jiraProperties.getUrl());
    this.historyJql = jiraProperties.getHistoryJql();
    this.actualJql = jiraProperties.getActualJql();
    this.maxResults = Integer.valueOf(jiraProperties.getIssuesPerRequest());
    this.startAt = Integer.valueOf(jiraProperties.getExtractFromIssue());
    this.delayAfterEveryRequest = Integer.valueOf(jiraProperties.getDelayBetweenRequests());
  }

  public void createHistoryIssuesDataset(String datasetFileName) {
    DataConsumer dataConsumer = new FileDataConsumer(new File(datasetFileName));
    dataRetriever = getRetriever(historyJql, dataConsumer);
    dataRetriever.start();
  }

  public List<Issue> getActualIssues() {
    List<Issue> result = new ArrayList<>();
    DataConsumer dataConsumer = new ListDataConsumer(result);
    dataRetriever = getRetriever(actualJql, dataConsumer);
    dataRetriever.start();
    return result;
  }

  private DataRetriever getRetriever(String jql, DataConsumer dataConsumer) {
    return new BasicDataRetriever(jiraClient, dataConsumer, jql, maxResults, startAt, delayAfterEveryRequest);
  }

  public double getStatus() {
    if (dataRetriever == null) {
      return -1.0;
    }
    return dataRetriever.getStatus();
  }

}
