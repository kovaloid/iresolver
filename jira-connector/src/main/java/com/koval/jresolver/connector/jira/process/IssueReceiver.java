package com.koval.jresolver.connector.jira.process;

import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.bean.JiraSearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.deliver.DataConsumer;
import com.koval.jresolver.connector.jira.process.impl.BasicDataRetriever2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueReceiver {
  private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataRetriever2.class);

  private final JiraClient client;
  private final IssueHandler handler;
  private final String jql;
  private final int issuesPerRequest;
  private int startAt;
  private final int finishAt;
  private final int delayAfterEveryRequest;
  private final int totalIssues;

  public IssueReceiver(JiraClient client, IssueHandler handler, ConnectorProperties properties, boolean isResolved) {
    this.client = client;
    this.handler = handler;
    this.jql = isResolved ? properties.getResolvedJql() :  properties.getUnresolvedJql();
    this.issuesPerRequest = properties.getIssuesPerRequest();
    this.startAt = properties.getStartAtIssue();
    this.finishAt = 0;
    this.delayAfterEveryRequest = 0;
    this.totalIssues = getTotalIssues();
  }

  private int getTotalIssues() {
    JiraSearchResult searchResult = client.searchByJql(jql, 0, 0);
    return searchResult.getTotal();
  }

  public Collection<Issue> getBatch(int startAt) {
    JiraSearchResult searchResult = client.searchByJql(jql, issuesPerRequest, startAt);
    return searchResult.getIssues();
  }

  public void launch() {
    LOGGER.info("Receiving data started.");
    while (canGetNextBatch()) {
      Collection<Issue> issues = getBatch(startAt);
      issues.forEach(issue -> handler.perform(new JiraIssue(issue)));
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
