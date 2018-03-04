package com.koval.jresolver.extraction.configuration;


public class JiraExtractionProperties {

  private String jql;
  private int maxResults;
  private int startAt;
  private int delayAfterEveryRequest;

  public JiraExtractionProperties() {
  }

  public JiraExtractionProperties(int maxResults, int startAt, int delayAfterEveryRequest) {
    this.maxResults = maxResults;
    this.startAt = startAt;
    this.delayAfterEveryRequest = delayAfterEveryRequest;
  }

  public JiraExtractionProperties searchJqlRequest(String jql) {
    this.jql = jql;
    return this;
  }

  public JiraExtractionProperties maxResults(int maxResults) {
    this.maxResults = maxResults;
    return this;
  }

  public JiraExtractionProperties startAt(int startAt) {
    this.startAt = startAt;
    return this;
  }

  public JiraExtractionProperties delayAfterEveryRequest(int delayAfterEveryRequest) {
    this.delayAfterEveryRequest = delayAfterEveryRequest;
    return this;
  }

  public String getSearchJqlRequest() {
    return jql;
  }

  public int getMaxResults() {
    return maxResults;
  }

  public int getStartAt() {
    return startAt;
  }

  public int getDelayAfterEveryRequest() {
    return delayAfterEveryRequest;
  }
}
