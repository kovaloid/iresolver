package com.koval.resolver.common.api.bean.issue;

import java.net.URI;
import java.util.Objects;


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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueLink issueLink = (IssueLink)o;
    return Objects.equals(targetIssueKey, issueLink.targetIssueKey)
        && Objects.equals(issueLinkType, issueLink.issueLinkType)
        && Objects.equals(targetIssueUri, issueLink.targetIssueUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetIssueKey, issueLinkType, targetIssueUri);
  }
}
