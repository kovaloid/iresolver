package com.koval.resolver.common.api.configuration.component.connectors;


public class GitlabConnectorConfiguration {

  private String url;
  private String credentialsFolder;
  private String resolvedQuery;
  private String unresolvedQuery;
  private int batchSize;
  private int batchDelay;

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
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

  @Override
  public String toString() {
    return "GitlabConnectorConfiguration{"
        + "url='" + url + '\''
        + ", credentialsFolder='" + credentialsFolder + '\''
        + ", resolvedQuery='" + resolvedQuery + '\''
        + ", unresolvedQuery='" + unresolvedQuery + '\''
        + ", batchSize=" + batchSize
        + ", batchDelay=" + batchDelay
        + '}';
  }
}
