package com.koval.jresolver.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Field;

import com.koval.jresolver.connector.jira.JiraConnector;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.factory.JiraClientFactory;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsKeeper;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsProtector;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;
import com.koval.jresolver.exception.JresolverException;
import com.koval.jresolver.processor.ProcessExecutor;
import com.koval.jresolver.processor.result.IssueProcessingResult;
import com.koval.jresolver.processor.rules.RuleEngineProcessor;
import com.koval.jresolver.processor.rules.core.RuleEngine;
import com.koval.jresolver.processor.rules.core.impl.DroolsRuleEngine;
import com.koval.jresolver.processor.similarity.SimilarityProcessor;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.core.dataset.DataSetCreator;
import com.koval.jresolver.processor.similarity.core.model.VectorModel;
import com.koval.jresolver.processor.similarity.core.model.VectorModelCreator;
import com.koval.jresolver.processor.similarity.core.model.VectorModelSerializer;
import com.koval.jresolver.report.ReportGenerator;
import com.koval.jresolver.report.impl.HtmlReportGenerator;


public final class LaunchUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(LaunchUtil.class);

  private LaunchUtil() {
  }

  public static void createDataSet() {
    ConnectorProperties connectorProperties = new ConnectorProperties();
    SimilarityProcessorProperties similarityProcessorProperties = new SimilarityProcessorProperties();
    try (JiraClient jiraClient = getJiraClientInstance(connectorProperties)) {
      JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
      IssuesReceiver receiver = jiraConnector.getResolvedIssuesReceiver();
      DataSetCreator dataSetCreator = new DataSetCreator(receiver, similarityProcessorProperties);
      dataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create data set file.", e);
    }
  }

  public static void createVectorModel() {
    SimilarityProcessorProperties similarityProcessorProperties = new SimilarityProcessorProperties();
    VectorModelCreator vectorModelCreator = new VectorModelCreator(similarityProcessorProperties);
    File dataSetFile = new File(similarityProcessorProperties.getWorkFolder(), similarityProcessorProperties.getDataSetFileName());
    try {
      VectorModel vectorModel = vectorModelCreator.createFromFile(dataSetFile);
      VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(similarityProcessorProperties);
      vectorModelSerializer.serialize(vectorModel);
    } catch (IOException e) {
      LOGGER.error("Could not create vector model file.", e);
    }
  }

  public static void clean() {
    SimilarityProcessorProperties similarityProcessorProperties = new SimilarityProcessorProperties();
    try {
      File folder = new File(similarityProcessorProperties.getWorkFolder());
      if (folder.exists()) {
        FileUtils.cleanDirectory(new File(similarityProcessorProperties.getWorkFolder()));
        LOGGER.info("Folder {} was cleaned", similarityProcessorProperties.getWorkFolder());
      } else {
        LOGGER.warn("Folder {} does not exist", similarityProcessorProperties.getWorkFolder());
      }
    } catch (IOException e) {
      LOGGER.error("Could not clean folder: " + similarityProcessorProperties.getWorkFolder(), e);
    }
  }

  public static void run() {
    ConnectorProperties connectorProperties = new ConnectorProperties();
    SimilarityProcessorProperties similarityProcessorProperties = new SimilarityProcessorProperties();
    Collection<IssueProcessingResult> results = new ArrayList<>();
    try (JiraClient jiraClient = getJiraClientInstance(connectorProperties);
         RuleEngine ruleEngine = new DroolsRuleEngine()) {
      JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
      IssuesReceiver receiver = jiraConnector.getUnresolvedIssuesReceiver();
      ProcessExecutor executor = new ProcessExecutor()
          .add(new SimilarityProcessor(jiraClient, similarityProcessorProperties))
          .add(new RuleEngineProcessor(ruleEngine));
      while (receiver.hasNextIssues()) {
        results.addAll(executor.execute(receiver.getNextIssues()));
      }
    } catch (IOException e) {
      LOGGER.error("Could not run issues processing.", e);
    }
    try {
      ReportGenerator generator = new HtmlReportGenerator();
      generator.generate(results);
      openReport();
    } catch (IOException e) {
      LOGGER.error("Could not initialize report generator.", e);
    }
  }

  public static void printFields() {
    ConnectorProperties connectorProperties = new ConnectorProperties();
    try (JiraClient jiraClient = getJiraClientInstance(connectorProperties)) {
      Iterable<Field> fields = jiraClient.getFields();
      if (fields.iterator().hasNext()){
        LOGGER.info("Available fields:");
        fields.forEach( field -> LOGGER.info("Field '{}' with id {}", field.getName(), field.getId()));
      } else {
        LOGGER.info("Not exists available fields.");
      }
    } catch (IOException e) {
      LOGGER.error("Could not run issues processing.", e);
    }
  }

  private static void openReport() throws IOException {
    File file = new File("../output/index.html");
    try {
      Desktop.getDesktop().browse(getUriFromFile(file));
    } catch (UnsupportedOperationException e) {
      LOGGER.warn("Could not open browser on the current platform", e);
    }
  }

  private static URI getUriFromFile(File file) throws IOException {
    String prefix = "file:///";
    String path = file.getCanonicalPath().replace('\\', '/');
    return URI.create(prefix + path);
  }

  private static JiraClient getJiraClientInstance(ConnectorProperties connectorProperties) {
    JiraClient jiraClient;
    try {
      if (connectorProperties.isAnonymous()) {
        jiraClient = JiraClientFactory.getAnonymousJiraClient(connectorProperties.getUrl());
      } else {
        CredentialsProtector protector = new CredentialsProtector();
        CredentialsKeeper keeper = new CredentialsKeeper(protector, connectorProperties);
        Credentials credentials;
        if (keeper.isStored()) {
          credentials = keeper.load();
        } else {
          String username = CommandLineUtil.getUsername();
          String password = CommandLineUtil.getPassword();
          credentials = new Credentials(username, password);
          keeper.store(credentials);
        }
        jiraClient = JiraClientFactory.getBasicJiraClient(connectorProperties.getUrl(), credentials);
      }
    } catch (JiraConnectorException e) {
      throw new JresolverException("Could not initialize Jira client.", e);
    }
    return jiraClient;
  }
}
