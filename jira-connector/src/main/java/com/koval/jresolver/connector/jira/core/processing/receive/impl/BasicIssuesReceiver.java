package com.koval.jresolver.connector.jira.core.processing.receive.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.processing.receive.IssuesReceiver;
import com.koval.jresolver.connector.jira.util.CollectionsUtil;


public class BasicIssuesReceiver implements IssuesReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicIssuesReceiver.class);

  private final JiraClient client;
  private final String query;
  private final int issuesPerRequest;
  private int currentIssueIndex;
  private int finishIssueIndex;
  private final int delayAfterEveryRequest;
  private final int totalIssues;

  public BasicIssuesReceiver(JiraClient client, ConnectorProperties properties, boolean isResolvedMode) {
    this.client = client;
    this.query = isResolvedMode ? properties.getResolvedJql() :  properties.getUnresolvedJql();
    this.issuesPerRequest = properties.getIssuesPerRequest();
    this.currentIssueIndex = properties.getStartAtIssue();
    this.finishIssueIndex = properties.getFinishAtIssue();
    this.delayAfterEveryRequest = 0;
    this.totalIssues = getTotalIssues();
    if (finishIssueIndex > totalIssues) {
      this.finishIssueIndex = totalIssues;
    }
  }

  private int getTotalIssues() {
    return client.searchByJql(query, 0, 0).getTotal();
  }

  @Override
  public boolean hasNextIssues() {
    return currentIssueIndex < finishIssueIndex;
  }

  @Override
  public Collection<Issue> getNextIssues() {
    SearchResult searchResult = client.searchByJql(query, issuesPerRequest, currentIssueIndex);
    Collection<Issue> issues = CollectionsUtil.convert(searchResult.getIssues());
    LOGGER.info("Progress: " + currentIssueIndex + "/" + totalIssues + " Issues: " + issues.toString());
    currentIssueIndex += issuesPerRequest;
    delay();
    return issues;
  }

  private void delay() {
    try {
      Thread.sleep(delayAfterEveryRequest);
      LOGGER.info("Waiting for " + delayAfterEveryRequest + "ms ...");
    } catch (InterruptedException ignored) {
    }
  }
}
