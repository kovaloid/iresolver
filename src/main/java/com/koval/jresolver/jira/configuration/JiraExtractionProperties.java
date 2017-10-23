package com.koval.jresolver.jira.configuration;


public class JiraExtractionProperties {

  private int maxResults;
  private int startAt;
  private int delayAfterEveryMaxResults;

  public JiraExtractionProperties() {
  }

  public JiraExtractionProperties(int maxResults, int startAt, int delayAfterEveryMaxResults) {
    this.maxResults = maxResults;
    this.startAt = startAt;
    this.delayAfterEveryMaxResults = delayAfterEveryMaxResults;
  }

  public JiraExtractionProperties maxResults(int maxResults) {
    this.maxResults = maxResults;
    return this;
  }

  public JiraExtractionProperties startAt(int startAt) {
    this.startAt = startAt;
    return this;
  }

  public JiraExtractionProperties delayAfterEveryMaxResults(int delayAfterEveryMaxResults) {
    this.delayAfterEveryMaxResults = delayAfterEveryMaxResults;
    return this;
  }

  public int getMaxResults() {
    return maxResults;
  }

  public int getStartAt() {
    return startAt;
  }

  public int getDelayAfterEveryMaxResults() {
    return delayAfterEveryMaxResults;
  }
}
