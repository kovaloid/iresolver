package com.koval.jresolver.connector.jira.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.common.api.component.connector.ProgressMonitor;
import com.koval.jresolver.connector.jira.configuration.JiraConnectorProperties;


public class JiraIssueReceiver implements IssueReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueReceiver.class);

  private final ProgressMonitor progressMonitor;
  private final IssueClient client;
  private final String query;
  private final Set<String> fields;
  private final int batchSize;
  private int currentIndex;
  private final int finishIndex;
  private final int batchDelay;

  public JiraIssueReceiver(IssueClient client, JiraConnectorProperties properties, boolean isResolvedMode) {
    this.client = client;
    this.query = isResolvedMode ? properties.getResolvedQuery() : properties.getUnresolvedQuery();
    this.fields = isResolvedMode ? properties.getResolvedIssueFields() : properties.getUnresolvedIssueFields();
    this.batchSize = properties.getBatchSize();
    this.batchDelay = properties.getBatchDelay();
    this.currentIndex = 0;
    this.finishIndex = getTotalIssues();
    this.progressMonitor = new ProgressMonitor(batchSize, finishIndex);
    this.progressMonitor.startMeasuringTotalTime();
  }

  private int getTotalIssues() {
    int total = client.getTotalIssues(query);
    LOGGER.info("Total number of issues: {}", total);
    return total;
  }

  @Override
  public boolean hasNextIssues() {
    boolean result = currentIndex < finishIndex;
    if (!result) {
      progressMonitor.endMeasuringTotalTime();
      LOGGER.info("Time spent: {}", progressMonitor.getFormattedSpentTime());
    }
    return result;
  }

  @Override
  public List<Issue> getNextIssues() {
    List<Issue> searchResult = client.search(query, batchSize, currentIndex, new ArrayList<>(fields));
    searchResult.forEach(issue ->
        LOGGER.info("{}: {} summary words, {} description words, {} comments, {} attachments", issue.getKey(),
            countWords(issue.getSummary()), countWords(issue.getDescription()), issue.getComments().size(),
            issue.getAttachments().size()));
    currentIndex += batchSize;
    LOGGER.info("Progress {}/{}", (currentIndex > finishIndex) ? finishIndex : currentIndex, finishIndex);
    if (batchDelay != 0) {
      delay();
    }
    progressMonitor.endMeasuringBatchTime();
    LOGGER.info("Remaining time: {}", progressMonitor.getFormattedRemainingTime((currentIndex > finishIndex) ? finishIndex : currentIndex));
    return searchResult;
  }

  private int countWords(String text) {
    if (text == null) {
      return 0;
    }
    String trimmedText = text.trim();
    if (trimmedText.isEmpty()) {
      return 0;
    }
    return trimmedText.split("\\s+").length;
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
