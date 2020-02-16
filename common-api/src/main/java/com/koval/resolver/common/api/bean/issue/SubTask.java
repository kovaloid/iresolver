package com.koval.resolver.common.api.bean.issue;

import java.net.URI;
import java.util.Objects;


public class SubTask {

  private String issueKey;
  private URI issueUri;
  private String summary;
  private IssueType issueType;
  private String status;

  public SubTask() {
  }

  public SubTask(String issueKey, URI issueUri, String summary, IssueType issueType, String status) {
    this.issueKey = issueKey;
    this.issueUri = issueUri;
    this.summary = summary;
    this.issueType = issueType;
    this.status = status;
  }

  public String getIssueKey() {
    return issueKey;
  }

  public void setIssueKey(String issueKey) {
    this.issueKey = issueKey;
  }

  public URI getIssueUri() {
    return issueUri;
  }

  public void setIssueUri(URI issueUri) {
    this.issueUri = issueUri;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public IssueType getIssueType() {
    return issueType;
  }

  public void setIssueType(IssueType issueType) {
    this.issueType = issueType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubTask subTask = (SubTask)o;
    return Objects.equals(issueKey, subTask.issueKey)
        && Objects.equals(issueUri, subTask.issueUri)
        && Objects.equals(summary, subTask.summary)
        && Objects.equals(issueType, subTask.issueType)
        && Objects.equals(status, subTask.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issueKey, issueUri, summary, issueType, status);
  }
}
