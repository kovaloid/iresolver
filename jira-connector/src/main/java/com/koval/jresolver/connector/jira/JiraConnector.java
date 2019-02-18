package com.koval.jresolver.connector.jira;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.deliver.DataConsumer;
import com.koval.jresolver.connector.jira.deliver.impl.FileDataConsumer;
import com.koval.jresolver.connector.jira.deliver.impl.ListDataConsumer;
import com.koval.jresolver.connector.jira.process.DataRetriever;
import com.koval.jresolver.connector.jira.process.impl.BasicDataRetriever;


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

  public JiraConnector() throws IOException, URISyntaxException {
    this(new ConnectorProperties());
  }

  public JiraConnector(ConnectorProperties connectorProperties) throws URISyntaxException {
    if (connectorProperties.isAnonymous()) {
      this.jiraClient = new BasicJiraClient(connectorProperties.getUrl());
    } else {
      this.jiraClient = new BasicJiraClient(connectorProperties.getUrl(), connectorProperties.getUsername(), connectorProperties.getPassword());
    }
    this.historyJql = connectorProperties.getResolvedJql();
    this.actualJql = connectorProperties.getUnresolvedJql();
    this.startAtIssue = connectorProperties.getStartAtIssue();
    this.issuesPerRequest = connectorProperties.getIssuesPerRequest();
    this.delayBetweenRequests = connectorProperties.getDelayBetweenRequests();
    this.maxIssues = connectorProperties.getMaxIssues();
    this.appendToDataSet = connectorProperties.isAppendToDataSet();
    this.workFolder = connectorProperties.getWorkFolder();
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

  public void createResolvedDataSet() throws IOException {
    File dataSetFile = new File(workFolder, "DataSet.txt");
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

  public List<JiraIssue> getUnresolvedIssues() {
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
