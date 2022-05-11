package com.koval.resolver.connector.jira.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.component.connector.ProgressMonitor;
import com.koval.resolver.common.api.configuration.component.connectors.JiraConnectorConfiguration;
import com.koval.resolver.common.api.model.issue.Issue;


public class JiraIssueReceiver implements IssueReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueReceiver.class);

  private final ProgressMonitor progressMonitor;
  private final IssueClient client;
  private final String query;
  private final List<String> fields;
  private final int batchSize;
  private final int batchDelay;
  private final int finishIndex;
  private int currentIndex;

  public JiraIssueReceiver(
    final IssueClient client, final JiraConnectorConfiguration properties, final boolean isResolvedMode) {
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

  @Override
  public boolean hasNextIssues() {
    final boolean result = currentIndex < finishIndex;
    if (!result) {
      progressMonitor.endMeasuringTotalTime();
      LOGGER.info("Time spent: {}", progressMonitor.getFormattedSpentTime());
    }
    return result;
  }

  @Override
  public List<Issue> getNextIssues() {
    final List<Issue> searchResult = client.search(query, batchSize, currentIndex, fields);
    searchResult.forEach(issue ->
                           LOGGER.info("{}: {} summary words, {} description words, {} comments, {} attachments",
                                       issue.getKey(),
                                       countWords(issue.getSummary()), countWords(issue.getDescription()),
                                       issue.getComments().size(),
                                       issue.getAttachments().size())
                        );
    currentIndex += batchSize;
    LOGGER.info("Progress {}/{}", Math.min(currentIndex, finishIndex), finishIndex);
    if (batchDelay != 0) {
      delay();
    }
    progressMonitor.endMeasuringBatchTime();
    LOGGER.info("Remaining time: {}", progressMonitor.getFormattedRemainingTime(
      Math.min(currentIndex, finishIndex)));
    return searchResult;
  }

  private int getTotalIssues() {
    final int total = client.getTotalIssues(query);
    LOGGER.info("Total number of issues: {}", total);
    return total;
  }

  private int countWords(final String text) {
    if (text == null) {
      return 0;
    }
    final String trimmedText = text.trim();
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
