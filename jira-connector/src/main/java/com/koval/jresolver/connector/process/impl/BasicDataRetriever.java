package com.koval.jresolver.connector.process.impl;

import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.configuration.JiraExtractionProperties;
import com.koval.jresolver.connector.deliver.DataConsumer;
import com.koval.jresolver.connector.process.DataRetriever;


public class BasicDataRetriever implements DataRetriever {
  private JiraClient jiraClient;
  private DataConsumer dataConsumer;
  private String jql;
  private int maxResults;
  private int startAt;
  private int delayAfterEveryRequest;
  private double status = 0.0;
  private boolean isComplete = false;

  public BasicDataRetriever(JiraClient jiraClient, DataConsumer dataConsumer, JiraExtractionProperties jiraExtractionProperties) {
    this(jiraClient, dataConsumer, jiraExtractionProperties.getSearchJqlRequest(),
      jiraExtractionProperties.getMaxResults(), jiraExtractionProperties.getStartAt(),
      jiraExtractionProperties.getDelayAfterEveryRequest());
  }

  public BasicDataRetriever(JiraClient jiraClient, DataConsumer dataConsumer, String jql, int maxResults, int startAt, int delayAfterEveryRequest) {
    this.jiraClient = jiraClient;
    this.dataConsumer = dataConsumer;
    this.jql = jql;
    this.maxResults = maxResults;
    this.startAt = startAt;
    this.delayAfterEveryRequest = delayAfterEveryRequest;
  }

  @Override
  public void start() {
    isComplete = false;
    while (!isComplete) {
      SearchResult searchResult = jiraClient.searchByJql(jql, maxResults, startAt);

      searchResult.getIssues().forEach((BasicIssue basicIssue) -> {
        Issue issue = jiraClient.getIssueByKey(basicIssue.getKey());
        dataConsumer.consume(issue);
        System.out.println("--------------------");
        System.out.println(searchResult.getStartIndex());
        System.out.println(searchResult.getMaxResults());
        System.out.println(searchResult.getTotal());
        System.out.println("--------------------");
      });

      startAt += maxResults;
      setStatus(searchResult);
      if (searchResult.getStartIndex() > searchResult.getTotal()) {
        break;
      } else {
        delay();
      }

    }
  }

  @Override
  public void stop() {
    isComplete = true;
  }

  private void delay() {
    try {
      Thread.sleep(delayAfterEveryRequest);
      System.out.println("Waiting...");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void setStatus(SearchResult searchResult) {
    status = (double) searchResult.getStartIndex() / searchResult.getTotal();
  }

  @Override
  public double getStatus() {
    return status;
  }
}
