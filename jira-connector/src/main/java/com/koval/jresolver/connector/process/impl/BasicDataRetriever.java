package com.koval.jresolver.connector.process.impl;

import java.util.Iterator;

import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.koval.jresolver.connector.client.JiraClient;
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

  private int maxIssues = 10;

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
    status = 0.0;
    isComplete = false;
    System.out.println("Receiving data started.");
    while (!isComplete) {
      SearchResult searchResult = jiraClient.searchByJql(jql, maxResults, startAt);

      Iterator<? extends BasicIssue> issueIterator = searchResult.getIssues().iterator();

      if (!issueIterator.hasNext()) {
        System.out.println("All data was collected. Stop receiving.");
        break;
      }

      int index = 0;
      while (issueIterator.hasNext()) {
        BasicIssue basicIssue = issueIterator.next();
        Issue issue = jiraClient.getIssueByKey(basicIssue.getKey());
        dataConsumer.consume(issue);
        index++;
        System.out.println("Progress: " + (startAt + index) + "/" + searchResult.getTotal() + " Issue: " + issue.getKey());
        if (startAt + index >= maxIssues) {
          System.out.println("Max issues number was reached. Stop receiving.");
          isComplete = true;
          break;
        }
      }

      setStatus((double) (startAt + index)/searchResult.getTotal());
      startAt += maxResults;
      delay();
    }
    System.out.println("Receiving data stopped.");
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

  private void setStatus(double status) {
    this.status = status;
  }

  @Override
  public double getStatus() {
    return status;
  }
}
