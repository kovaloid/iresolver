package com.koval.jresolver.common.api.configuration.bean.connectors;


public class BugzillaConnectorConfiguration {

  private String url;
  private boolean anonymous;
  private String credentialsFolder;
  private String resolvedQuery;
  private String unresolvedQuery;
  private int batchSize;
  private int batchDelay;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public String getCredentialsFolder() {
    return credentialsFolder;
  }

  public void setCredentialsFolder(String credentialsFolder) {
    this.credentialsFolder = credentialsFolder;
  }

  public String getResolvedQuery() {
    return resolvedQuery;
  }

  public void setResolvedQuery(String resolvedQuery) {
    this.resolvedQuery = resolvedQuery;
  }

  public String getUnresolvedQuery() {
    return unresolvedQuery;
  }

  public void setUnresolvedQuery(String unresolvedQuery) {
    this.unresolvedQuery = unresolvedQuery;
  }

  public int getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(int batchSize) {
    this.batchSize = batchSize;
  }

  public int getBatchDelay() {
    return batchDelay;
  }

  public void setBatchDelay(int batchDelay) {
    this.batchDelay = batchDelay;
  }

  @Override
  public String toString() {
    return "BugzillaConnectorConfiguration{"
        + "url='" + url + '\''
        + ", anonymous=" + anonymous
        + ", credentialsFolder='" + credentialsFolder + '\''
        + ", resolvedQuery='" + resolvedQuery + '\''
        + ", unresolvedQuery='" + unresolvedQuery + '\''
        + ", batchSize=" + batchSize
        + ", batchDelay=" + batchDelay
        + '}';
  }
}
