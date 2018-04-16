package com.koval.jresolver.connector.configuration;

import com.koval.jresolver.connector.JiraConnector;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class JiraProperties {

  private String url;
  private String historyJql;
  private String actualJql;
  private String extractFromIssue;
  private String issuesPerRequest;
  private String delayBetweenRequests;

  public JiraProperties() {
  }

  public JiraProperties(String propertiesFileName) {
    Properties properties = new Properties();
    try (InputStream input = JiraConnector.class.getClassLoader().getResourceAsStream(propertiesFileName)) {

      if (input == null) {
        System.out.println("Could not find " + propertiesFileName);
        return;
      }

      properties.load(input);

      //get the property value and print it out
      //System.out.println(properties.getProperty("url"));
      //System.out.println(properties.getProperty("historyJql"));
      //System.out.println(properties.getProperty("actualJql"));
      //System.out.println(properties.getProperty("extractFromIssue"));
      //System.out.println(properties.getProperty("issuesPerRequest"));
      //System.out.println(properties.getProperty("delayBetweenRequests"));

      url = properties.getProperty("url");
      historyJql = properties.getProperty("historyJql");
      actualJql = properties.getProperty("actualJql");
      extractFromIssue = properties.getProperty("extractFromIssue");
      issuesPerRequest = properties.getProperty("issuesPerRequest");
      delayBetweenRequests = properties.getProperty("delayBetweenRequests");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public String getIssuesPerRequest() {
    return issuesPerRequest;
  }

  public void setIssuesPerRequest(String issuesPerRequest) {
    this.issuesPerRequest = issuesPerRequest;
  }

  public String getExtractFromIssue() {
    return extractFromIssue;
  }

  public void setExtractFromIssue(String extractFromIssue) {
    this.extractFromIssue = extractFromIssue;
  }

  public String getDelayBetweenRequests() {
    return delayBetweenRequests;
  }

  public void setDelayBetweenRequests(String delayBetweenRequests) {
    this.delayBetweenRequests = delayBetweenRequests;
  }
}
