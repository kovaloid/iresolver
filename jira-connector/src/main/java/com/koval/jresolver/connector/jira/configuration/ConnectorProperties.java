package com.koval.jresolver.connector.jira.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConnectorProperties {

  private static final String CONNECTOR_PROPERTIES_FILE = "connector.properties";

  private String url;
  private boolean isAnonymous;
  private String resolvedJql;
  private String unresolvedJql;
  private int startAtIssue;
  private int finishAtIssue;
  private int issuesPerRequest = 10;
  private int delayBetweenRequests = 3000;
  private String searchFields;
  private String workFolder = "../data/";
  private String dataSetFileName = "DataSet.txt";

  public ConnectorProperties() throws IOException {
    loadProperties();
  }

  private void loadProperties() throws IOException {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONNECTOR_PROPERTIES_FILE)) {
      properties.load(input);
      url = properties.getProperty("url");
      isAnonymous = Boolean.parseBoolean(properties.getProperty("isAnonymous"));
      resolvedJql = properties.getProperty("resolvedJql");
      unresolvedJql = properties.getProperty("unresolvedJql");
      startAtIssue = Integer.parseInt(properties.getProperty("startAtIssue"));
      finishAtIssue = Integer.parseInt(properties.getProperty("finishAtIssue"));
      issuesPerRequest = Integer.parseInt(properties.getProperty("issuesPerRequest"));
      delayBetweenRequests = Integer.parseInt(properties.getProperty("delayBetweenRequests"));
      searchFields = properties.getProperty("searchFields");
      workFolder = properties.getProperty("workFolder");
      dataSetFileName = properties.getProperty("dataSetFileName");
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isAnonymous() {
    return isAnonymous;
  }

  public void setAnonymous(boolean anonymous) {
    isAnonymous = anonymous;
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

  public int getStartAtIssue() {
    return startAtIssue;
  }

  public void setStartAtIssue(int startAtIssue) {
    this.startAtIssue = startAtIssue;
  }

  public int getFinishAtIssue() {
    return finishAtIssue;
  }

  public void setFinishAtIssue(int finishAtIssue) {
    this.finishAtIssue = finishAtIssue;
  }

  public int getIssuesPerRequest() {
    return issuesPerRequest;
  }

  public void setIssuesPerRequest(int issuesPerRequest) {
    this.issuesPerRequest = issuesPerRequest;
  }

  public int getDelayBetweenRequests() {
    return delayBetweenRequests;
  }

  public void setDelayBetweenRequests(int delayBetweenRequests) {
    this.delayBetweenRequests = delayBetweenRequests;
  }

  public String getSearchFields() {
    return searchFields;
  }

  public void setSearchFields(String searchFields) {
    this.searchFields = searchFields;
  }

  public String getWorkFolder() {
    return workFolder;
  }

  public void setWorkFolder(String workFolder) {
    this.workFolder = workFolder;
  }

  public String getDataSetFileName() {
    return dataSetFileName;
  }

  public void setDataSetFileName(String dataSetFileName) {
    this.dataSetFileName = dataSetFileName;
  }
}
