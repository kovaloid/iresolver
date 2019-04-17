package com.koval.jresolver.common.api.bean.issue;

import java.net.URI;


public class IssueLink {

  private String targetIssueKey;
  private URI targetIssueUri;

  public String getTargetIssueKey() {
    return targetIssueKey;
  }

  public void setTargetIssueKey(String targetIssueKey) {
    this.targetIssueKey = targetIssueKey;
  }

  public URI getTargetIssueUri() {
    return targetIssueUri;
  }

  public void setTargetIssueUri(URI targetIssueUri) {
    this.targetIssueUri = targetIssueUri;
  }
}
