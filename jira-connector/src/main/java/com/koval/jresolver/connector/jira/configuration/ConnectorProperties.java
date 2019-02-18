package com.koval.jresolver.connector.jira.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConnectorProperties {

  private static final String CONNECTOR_PROPERTIES_FILE = "connector.properties";

  private boolean isLoaded;

  private String url;
  private String username;
  private String password;
  private String resolvedJql;
  private String unresolvedJql;
  private int startAtIssue;
  private int issuesPerRequest = 10;
  private int delayBetweenRequests = 3000;
  private int maxIssues = 10;
  private boolean appendToDataSet = true;
  private String workFolder = "../data/";

  public ConnectorProperties() throws IOException {
    if (!isLoaded) {
      loadProperties();
    }
  }

  private void loadProperties() throws IOException {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONNECTOR_PROPERTIES_FILE)) {
      properties.load(input);
      url = properties.getProperty("url");
      username = properties.getProperty("username");
      password = properties.getProperty("password");
      resolvedJql = properties.getProperty("resolvedJql");
      unresolvedJql = properties.getProperty("unresolvedJql");
      startAtIssue = Integer.parseInt(properties.getProperty("startAtIssue"));
      issuesPerRequest = Integer.parseInt(properties.getProperty("issuesPerRequest"));
      delayBetweenRequests = Integer.parseInt(properties.getProperty("delayBetweenRequests"));
      maxIssues = Integer.parseInt(properties.getProperty("maxIssues"));
      appendToDataSet = Boolean.parseBoolean(properties.getProperty("appendToDataSet"));
      workFolder = properties.getProperty("workFolder");
      isLoaded = true;
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getResolvedJql() {
    return resolvedJql;
  }

  public void setResolvedJql(String resolvedJql) {
    this.resolvedJql = resolvedJql;
  }

  public String getUnresolvedJql() {
    return unresolvedJql;
  }

  public void setUnresolvedJql(String unresolvedJql) {
    this.unresolvedJql = unresolvedJql;
  }

  public int getIssuesPerRequest() {
    return issuesPerRequest;
  }

  public void setIssuesPerRequest(int issuesPerRequest) {
    this.issuesPerRequest = issuesPerRequest;
  }

  public int getStartAtIssue() {
    return startAtIssue;
  }

  public void setStartAtIssue(int startAtIssue) {
    this.startAtIssue = startAtIssue;
  }

  public int getDelayBetweenRequests() {
    return delayBetweenRequests;
  }

  public void setDelayBetweenRequests(int delayBetweenRequests) {
    this.delayBetweenRequests = delayBetweenRequests;
  }

  public int getMaxIssues() {
    return maxIssues;
  }

  public void setMaxIssues(int maxIssues) {
    this.maxIssues = maxIssues;
  }

  public boolean isAppendToDataSet() {
    return appendToDataSet;
  }

  public void setAppendToDataSet(boolean appendToDataSet) {
    this.appendToDataSet = appendToDataSet;
  }

  public boolean isAnonymous() {
    return username == null || username.isEmpty();
  }

  public String getWorkFolder() {
    return workFolder;
  }

  public void setWorkFolder(String workFolder) {
    this.workFolder = workFolder;
  }
}
