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

  public SubTask(
    final String issueKey, final URI issueUri, final String summary, final IssueType issueType, final String status) {
    this.issueKey = issueKey;
    this.issueUri = issueUri;
    this.summary = summary;
    this.issueType = issueType;
    this.status = status;
  }

  public String getIssueKey() {
    return issueKey;
  }

  public void setIssueKey(final String issueKey) {
    this.issueKey = issueKey;
  }

  public URI getIssueUri() {
    return issueUri;
  }

  public void setIssueUri(final URI issueUri) {
    this.issueUri = issueUri;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(final String summary) {
    this.summary = summary;
  }

  public IssueType getIssueType() {
    return issueType;
  }

  public void setIssueType(final IssueType issueType) {
    this.issueType = issueType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(issueKey, issueUri, summary, issueType, status);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SubTask subTask = (SubTask)o;
    return Objects.equals(issueKey, subTask.issueKey)
           && Objects.equals(issueUri, subTask.issueUri)
           && Objects.equals(summary, subTask.summary)
           && Objects.equals(issueType, subTask.issueType)
           && Objects.equals(status, subTask.status);
  }

}
