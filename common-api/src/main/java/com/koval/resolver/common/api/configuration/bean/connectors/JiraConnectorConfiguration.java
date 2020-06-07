package com.koval.resolver.common.api.configuration.bean.connectors;

import java.util.List;


public class JiraConnectorConfiguration {

  private String url;
  private boolean anonymous;
  private String credentialsFolder;
  private String resolvedQuery;
  private String unresolvedQuery;
  private int batchSize;
  private int batchDelay;
  private String issueFieldsCsvFile;
  private List<String> resolvedIssueFields;
  private List<String> unresolvedIssueFields;

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(final boolean anonymous) {
    this.anonymous = anonymous;
  }

  public String getCredentialsFolder() {
    return credentialsFolder;
  }

  public void setCredentialsFolder(final String credentialsFolder) {
    this.credentialsFolder = credentialsFolder;
  }

  public String getResolvedQuery() {
    return resolvedQuery;
  }

  public void setResolvedQuery(final String resolvedQuery) {
    this.resolvedQuery = resolvedQuery;
  }

  public String getUnresolvedQuery() {
    return unresolvedQuery;
  }

  public void setUnresolvedQuery(final String unresolvedQuery) {
    this.unresolvedQuery = unresolvedQuery;
  }

  public int getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(final int batchSize) {
    this.batchSize = batchSize;
  }

  public int getBatchDelay() {
    return batchDelay;
  }

  public void setBatchDelay(final int batchDelay) {
    this.batchDelay = batchDelay;
  }

  public List<String> getResolvedIssueFields() {
    return resolvedIssueFields;
  }

  public void setResolvedIssueFields(final List<String> resolvedIssueFields) {
    this.resolvedIssueFields = resolvedIssueFields;
  }

  public List<String> getUnresolvedIssueFields() {
    return unresolvedIssueFields;
  }

  public void setUnresolvedIssueFields(final List<String> unresolvedIssueFields) {
    this.unresolvedIssueFields = unresolvedIssueFields;
  }

  public String getIssueFieldsCsvFile() {
    return issueFieldsCsvFile;
  }

  public void setIssueFieldsCsvFile(String issueFieldsCsvFile) {
    this.issueFieldsCsvFile = issueFieldsCsvFile;
  }

  @Override
  public String toString() {
    return "JiraConnectorConfiguration{"
            + "url='" + url + '\''
            + ", anonymous=" + anonymous
            + ", credentialsFolder='" + credentialsFolder + '\''
            + ", resolvedQuery='" + resolvedQuery + '\''
            + ", unresolvedQuery='" + unresolvedQuery + '\''
            + ", batchSize=" + batchSize
            + ", batchDelay=" + batchDelay
            + ", resolvedIssueFields=" + resolvedIssueFields
            + ", unresolvedIssueFields=" + unresolvedIssueFields
            + ", issueFieldsCsvFile='" + issueFieldsCsvFile + '\''
            + '}';
  }
}
