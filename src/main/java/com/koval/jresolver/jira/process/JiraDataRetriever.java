package com.koval.jresolver.jira.process;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.koval.jresolver.jira.configuration.JiraExtractionProperties;
import com.koval.jresolver.jira.client.JiraClient;
import com.koval.jresolver.jira.bean.PreparedJiraIssue;
import com.koval.jresolver.jira.configuration.JiraRequestProperties;
import com.koval.jresolver.resolver.Consumer;
import com.koval.jresolver.resolver.DataRetriever;


public class JiraDataRetriever implements DataRetriever {

  private JiraExtractionProperties extraction;
  private int totalResults;
  private int maxResults;
  private Consumer<PreparedJiraIssue> consumer;
  private JiraRequestHandler handler;

  public JiraDataRetriever(JiraClient client, JiraRequestProperties request, JiraExtractionProperties extraction, Consumer<PreparedJiraIssue> consumer) {
    this.extraction = extraction;
    this.maxResults = extraction.getMaxResults();
    this.consumer = consumer;
    this.handler = new JiraRequestHandler(client, generateJqlRequest(request));
  }

  private String generateJqlRequest(JiraRequestProperties request) {
    return "project=" + request.getProjectName();
  }

  public int getTotal() {
    handler.process(0, 0);
    return handler.getTotalResults();
  }

  public void start() {
    int startAt = extraction.getStartAt();
    totalResults = maxResults;
    while (startAt < totalResults) {
      startOneIteration(startAt);
      delay();
      startAt += maxResults;
    }
  }

  private void startOneIteration(int startAt) {
    List<PreparedJiraIssue> raw = handler.process(maxResults, startAt);
    totalResults = handler.getTotalResults();
    consumer.consume(raw);
  }

  private void delay() {
    try {
      TimeUnit.SECONDS.sleep(extraction.getDelayAfterEveryMaxResults());
      System.out.println("Waiting...");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
