package com.koval.jresolver.connector.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.koval.jresolver.manager.Manager;


public class JiraProperties {

  private String url;
  private String username;
  private String password;
  private String historyJql;
  private String actualJql;
  private int startAtIssue;
  private int issuesPerRequest = 10;
  private int delayBetweenRequests = 3000;
  private int maxIssues = 10;
  private boolean appendToDataSet = true;

  public JiraProperties(String url, String historyJql, String actualJql) {
    this.url = url;
    this.historyJql = historyJql;
    this.actualJql = actualJql;
  }

  public JiraProperties(String propertiesFileName) throws IOException {
    Properties properties = new Properties();
    try (InputStream input = new FileInputStream(Manager.getConfigDirectory() + propertiesFileName)) {
      properties.load(input);
      url = properties.getProperty("url");
      username = properties.getProperty("username");
      password = properties.getProperty("password");
      historyJql = properties.getProperty("historyJql");
      actualJql = properties.getProperty("actualJql");
      startAtIssue = Integer.parseInt(properties.getProperty("startAtIssue"));
      issuesPerRequest = Integer.parseInt(properties.getProperty("issuesPerRequest"));
      delayBetweenRequests = Integer.parseInt(properties.getProperty("delayBetweenRequests"));
      maxIssues = Integer.parseInt(properties.getProperty("maxIssues"));
      appendToDataSet = Boolean.parseBoolean(properties.getProperty("appendToDataSet"));
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

  public String getHistoryJql() {
    return historyJql;
  }

  public void setHistoryJql(String historyJql) {
    this.historyJql = historyJql;
  }

  public String getActualJql() {
    return actualJql;
  }

  public void setActualJql(String actualJql) {
    this.actualJql = actualJql;
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
    return username == null || password == null || username.isEmpty() || password.isEmpty();
  }

}
