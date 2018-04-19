package com.koval.jresolver.connector;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.connector.deliver.DataConsumer;
import com.koval.jresolver.connector.deliver.impl.FileDataConsumer;
import com.koval.jresolver.connector.deliver.impl.ListDataConsumer;
import com.koval.jresolver.connector.process.DataRetriever;
import com.koval.jresolver.connector.process.impl.BasicDataRetriever;


public class JiraConnector {

  private JiraClient jiraClient;
  private DataRetriever dataRetriever;
  private final String historyJql;
  private final String actualJql;
  private final int startAtIssue;
  private final int issuesPerRequest;
  private final int delayBetweenRequests;
  private final int maxIssues;
  private final boolean appendToDataSet;
  private final String workFolder;

  public JiraConnector(JiraProperties jiraProperties) throws URISyntaxException {
    if (jiraProperties.isAnonymous()) {
      this.jiraClient = new BasicJiraClient(jiraProperties.getUrl());
    } else {
      this.jiraClient = new BasicJiraClient(jiraProperties.getUrl(), jiraProperties.getUsername(), jiraProperties.getPassword());
    }
    this.historyJql = jiraProperties.getHistoryJql();
    this.actualJql = jiraProperties.getActualJql();
    this.startAtIssue = jiraProperties.getStartAtIssue();
    this.issuesPerRequest = jiraProperties.getIssuesPerRequest();
    this.delayBetweenRequests = jiraProperties.getDelayBetweenRequests();
    this.maxIssues = jiraProperties.getMaxIssues();
    this.appendToDataSet = jiraProperties.isAppendToDataSet();
    this.workFolder = jiraProperties.getWorkFolder();
  }

  public void createHistoryIssuesDataSet(String dataSetFileName) {
    DataConsumer dataConsumer = new FileDataConsumer(workFolder + dataSetFileName, appendToDataSet);
    dataRetriever = getRetriever(historyJql, dataConsumer);
    dataRetriever.start();
  }

  public List<JiraIssue> getActualIssues() {
    List<JiraIssue> result = new ArrayList<>();
    DataConsumer dataConsumer = new ListDataConsumer(result);
    dataRetriever = getRetriever(actualJql, dataConsumer);
    dataRetriever.start();
    return result;
  }

  private DataRetriever getRetriever(String jql, DataConsumer dataConsumer) {
    return new BasicDataRetriever(jiraClient, dataConsumer, jql, issuesPerRequest, startAtIssue, delayBetweenRequests, maxIssues);
  }
}
