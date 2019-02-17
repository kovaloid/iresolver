package com.koval.jresolver.connector2;

import com.koval.jresolver.connector2.bean.JiraIssue;
import com.koval.jresolver.connector2.client.JiraClient;
import com.koval.jresolver.connector2.client.impl.BasicJiraClient;
import com.koval.jresolver.connector2.configuration.JiraProperties;
import com.koval.jresolver.connector2.deliver.DataConsumer;
import com.koval.jresolver.connector2.deliver.impl.FileDataConsumer;
import com.koval.jresolver.connector2.deliver.impl.ListDataConsumer;
import com.koval.jresolver.connector2.process.DataRetriever;
import com.koval.jresolver.connector2.process.impl.BasicDataRetriever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class JiraConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraConnector.class);

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
    File folder = new File(workFolder);
    if (folder.exists()) {
      LOGGER.info("Folder exists: " + workFolder);
    } else {
      if (folder.mkdir()) {
        LOGGER.info("Folder created successfully: " + workFolder);
      } else {
        LOGGER.error("Folder creation failed: " + workFolder);
      }
    }
  }

  public void createHistoryIssuesDataSet(String dataSetFileName) throws IOException {
    File dataSetFile = new File(workFolder, dataSetFileName);
    if (!appendToDataSet) {
      if (dataSetFile.exists()) {
        dataSetFile.delete();
      }
      dataSetFile.createNewFile();
    }
    DataConsumer dataConsumer = new FileDataConsumer(dataSetFile);
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
