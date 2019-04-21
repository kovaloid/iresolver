package com.koval.jresolver.common.api.bean.issue;

import java.net.URI;


public class IssueLink {

  private String targetIssueKey;
  private String issueLinkType;
  private URI targetIssueUri;

  public IssueLink() {
  }

  public IssueLink(String targetIssueKey, String issueLinkType, URI targetIssueUri) {
    this.targetIssueKey = targetIssueKey;
    this.issueLinkType = issueLinkType;
    this.targetIssueUri = targetIssueUri;
  }

  public String getTargetIssueKey() {
    return targetIssueKey;
  }

  public void setTargetIssueKey(String targetIssueKey) {
    this.targetIssueKey = targetIssueKey;
  }

  public String getIssueLinkType() {
    return issueLinkType;
  }

  public void setIssueLinkType(String issueLinkType) {
    this.issueLinkType = issueLinkType;
  }

  public URI getTargetIssueUri() {
    return targetIssueUri;
  }

  public void setTargetIssueUri(URI targetIssueUri) {
    this.targetIssueUri = targetIssueUri;
  }
}
