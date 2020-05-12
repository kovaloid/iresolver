package com.koval.resolver.common.api.bean.issue;

import java.net.URI;
import java.util.Objects;


public class IssueLink {

  private String targetIssueKey;
  private String issueLinkType;
  private URI targetIssueUri;

  public IssueLink() {
  }

  public IssueLink(final String targetIssueKey, final String issueLinkType, final URI targetIssueUri) {
    this.targetIssueKey = targetIssueKey;
    this.issueLinkType = issueLinkType;
    this.targetIssueUri = targetIssueUri;
  }

  public String getTargetIssueKey() {
    return targetIssueKey;
  }

  public void setTargetIssueKey(final String targetIssueKey) {
    this.targetIssueKey = targetIssueKey;
  }

  public String getIssueLinkType() {
    return issueLinkType;
  }

  public void setIssueLinkType(final String issueLinkType) {
    this.issueLinkType = issueLinkType;
  }

  public URI getTargetIssueUri() {
    return targetIssueUri;
  }

  public void setTargetIssueUri(final URI targetIssueUri) {
    this.targetIssueUri = targetIssueUri;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final IssueLink issueLink = (IssueLink)o;
    return Objects.equals(targetIssueKey, issueLink.targetIssueKey)
        && Objects.equals(issueLinkType, issueLink.issueLinkType)
        && Objects.equals(targetIssueUri, issueLink.targetIssueUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetIssueKey, issueLinkType, targetIssueUri);
  }
}
