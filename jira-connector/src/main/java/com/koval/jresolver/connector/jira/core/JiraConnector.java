package com.koval.jresolver.connector.jira.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.processing.impl.FileIssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.impl.ObjectIssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.IssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.IssuesReceiver;
import com.koval.jresolver.connector.jira.core.processing.IssuesReceiverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;


public class JiraConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraConnector.class);

  private JiraClient jiraClient;
  private ConnectorProperties connectorProperties;

  public JiraConnector(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    this.jiraClient = jiraClient;
    this.connectorProperties = connectorProperties;
    String workFolderPath = connectorProperties.getWorkFolder();
    File folder = new File(workFolderPath);
    if (folder.exists()) {
      LOGGER.info("Folder exists: " + workFolderPath);
    } else {
      if (folder.mkdir()) {
        LOGGER.info("Folder created successfully: " + workFolderPath);
      } else {
        LOGGER.error("Folder creation failed: " + workFolderPath);
      }
    }
  }

  public void createResolvedDataSet() {
    IssuesReceiver receiver = IssuesReceiverFactory.forResolvedIssues(jiraClient, connectorProperties);
    IssuesGenerator<File> generator = new FileIssuesGenerator(receiver);
    generator.launch();
  }

  public Collection<Issue> getUnresolvedIssues() {
    IssuesReceiver receiver = IssuesReceiverFactory.forUnresolvedIssues(jiraClient, connectorProperties);
    IssuesGenerator<Collection<Issue>> generator = new ObjectIssuesGenerator(receiver);
    generator.launch();
    return generator.getResults();
  }
}
