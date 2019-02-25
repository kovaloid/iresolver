package com.koval.jresolver.connector.jira.core.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.connector.jira.util.CollectionsUtil;


public class IssuesReceiverImpl implements IssuesReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(IssuesReceiverImpl.class);

  private final JiraClient client;
  private final String query;
  private final int batchSize;
  private int currentIndex;
  private final int finishIndex;
  private final int batchDelay;

  public IssuesReceiverImpl(JiraClient client, ConnectorProperties properties, boolean isResolvedMode) {
    this.client = client;
    this.query = isResolvedMode ? properties.getResolvedQuery() : properties.getUnresolvedQuery();
    this.batchSize = properties.getBatchSize();
    this.batchDelay = properties.getBatchDelay();
    this.currentIndex = 0;
    this.finishIndex = getTotalIssues();
  }

  private int getTotalIssues() {
    LOGGER.info("Getting total issues...");
    return client.searchByJql(query, 0, 0).getTotal();
  }

  @Override
  public boolean hasNextIssues() {
    return currentIndex < finishIndex;
  }

  @Override
  public Collection<Issue> getNextIssues() {
    SearchResult searchResult = client.searchByJql(query, batchSize, currentIndex);
    searchResult.getIssues().forEach(issue -> LOGGER.info("{}: {}", issue.getKey(), issue.getSummary()));
    currentIndex += batchSize;
    LOGGER.info("Progress {}/{}", (currentIndex > finishIndex) ? finishIndex : currentIndex, finishIndex);
    if (batchDelay != 0) {
      delay();
    }
    return CollectionsUtil.convert(searchResult.getIssues());
  }

  private void delay() {
    try {
      LOGGER.info("Waiting for " + batchDelay + "ms ...");
      Thread.sleep(batchDelay);
    } catch (InterruptedException e) {
      LOGGER.error("Delay between batch requests was interrupted.", e);
    }
  }
}
