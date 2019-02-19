package com.koval.jresolver.connector.jira.core;

import java.io.File;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.processing.generate.IssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.generate.impl.CollectionIssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.generate.impl.FileIssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.receive.IssuesReceiver;
import com.koval.jresolver.connector.jira.core.processing.receive.impl.IssuesReceiverFactory;


public class JiraConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraConnector.class);

  private final JiraClient jiraClient;
  private final ConnectorProperties connectorProperties;

  public JiraConnector(JiraClient jiraClient, ConnectorProperties connectorProperties) {
    this.jiraClient = jiraClient;
    this.connectorProperties = connectorProperties;
    createWorkFolder();
  }

  private void createWorkFolder() {
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
    IssuesGenerator<Collection<Issue>> generator = new CollectionIssuesGenerator(receiver);
    generator.launch();
    return generator.getResults();
  }
}
