package com.koval.resolver.common.api.configuration.bean.connectors;

import java.util.List;


public class ConfluenceConnectorConfiguration {

  private String url;
  private boolean anonymous;
  private String credentialsFolder;
  private List<String> spaceKeys;
  private int limitPerRequest;

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

  public List<String> getSpaceKeys() {
    return spaceKeys;
  }

  public void setSpaceKeys(final List<String> spaceKeys) {
    this.spaceKeys = spaceKeys;
  }

  public int getLimitPerRequest() {
    return limitPerRequest;
  }

  public void setLimitPerRequest(final int limitPerRequest) {
    this.limitPerRequest = limitPerRequest;
  }

  @Override
  public String toString() {
    return "ConfluenceConnectorConfiguration{"
        + "url='" + url + '\''
        + ", anonymous=" + anonymous
        + ", credentialsFolder='" + credentialsFolder + '\''
        + ", spaceKeys=" + spaceKeys
        + ", limitPerRequest=" + limitPerRequest
        + '}';
  }
}
