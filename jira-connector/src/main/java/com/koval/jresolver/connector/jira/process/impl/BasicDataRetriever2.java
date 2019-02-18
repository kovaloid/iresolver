package com.koval.jresolver.connector.jira.process.impl;

import java.util.Collection;
import java.util.Iterator;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.bean.JiraSearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.deliver.DataConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasicDataRetriever2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataRetriever2.class);

  private final JiraClient jiraClient;
  private final DataConsumer dataConsumer;
  private final String jql;
  private final int issuesPerRequest;
  private int startAt;
  private final int finishAt;
  private final int delayAfterEveryRequest;
  private final int totalIssues;

  public BasicDataRetriever2(JiraClient jiraClient, DataConsumer dataConsumer, String jql, int issuesPerRequest, int startAt, int delayAfterEveryRequest, int finishAt) {
    this.jiraClient = jiraClient;
    this.dataConsumer = dataConsumer;
    this.jql = jql;
    this.issuesPerRequest = issuesPerRequest;
    this.startAt = startAt;
    this.finishAt = finishAt;
    this.delayAfterEveryRequest = delayAfterEveryRequest;
    this.totalIssues = getTotalIssues();
  }

  private int getTotalIssues() {
    JiraSearchResult searchResult = jiraClient.searchByJql(jql, 0, 0);
    return searchResult.getTotal();
  }

  public Collection<Issue> getBatch(int startAt) {
    JiraSearchResult searchResult = jiraClient.searchByJql(jql, issuesPerRequest, startAt);
    return searchResult.getIssues();
  }

  public void launch() {
    LOGGER.info("Receiving data started.");
    while (canGetNextBatch()) {
      Collection<Issue> issues = getBatch(startAt);
      issues.forEach(issue -> dataConsumer.consume(new JiraIssue(issue)));
      LOGGER.info("Progress: " + startAt + "/" + totalIssues + " Issues: " + issues.toString());
      startAt += issuesPerRequest;
      delay();
    }
    LOGGER.info("Receiving data stopped.");
  }

  public boolean canGetNextBatch() {
    return startAt >= finishAt || startAt >= totalIssues;
  }

  private void delay() {
    try {
      Thread.sleep(delayAfterEveryRequest);
      LOGGER.info("Waiting for " + delayAfterEveryRequest + "ms ...");
    } catch (InterruptedException ignored) {
    }
  }
}
