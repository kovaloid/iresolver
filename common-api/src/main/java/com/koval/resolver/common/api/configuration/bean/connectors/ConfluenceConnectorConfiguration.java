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

  public List<String> getSpaceKeys() {
    return spaceKeys;
  }

  public void setSpaceKeys(List<String> spaceKeys) {
    this.spaceKeys = spaceKeys;
  }

  public int getLimitPerRequest() {
    return limitPerRequest;
  }

  public void setLimitPerRequest(int limitPerRequest) {
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
