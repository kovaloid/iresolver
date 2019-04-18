package com.koval.jresolver.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.ProcessExecutor;
import com.koval.jresolver.common.api.auth.Credentials;
import com.koval.jresolver.common.api.auth.CredentialsKeeper;
import com.koval.jresolver.common.api.auth.CredentialsProtector;
import com.koval.jresolver.common.api.bean.issue.IssueField;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.common.api.component.reporter.ReportGenerator;
import com.koval.jresolver.connector.jira.JiraConnector;
import com.koval.jresolver.connector.jira.client.JiraIssueClientFactory;
import com.koval.jresolver.connector.jira.configuration.JiraConnectorProperties;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;
import com.koval.jresolver.exception.ResolverException;
import com.koval.jresolver.processor.rules.RuleEngineProcessor;
import com.koval.jresolver.processor.rules.core.RuleEngine;
import com.koval.jresolver.processor.rules.core.impl.DroolsRuleEngine;
import com.koval.jresolver.processor.similarity.SimilarityProcessor;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.core.dataset.DataSetCreator;
import com.koval.jresolver.processor.similarity.core.model.VectorModel;
import com.koval.jresolver.processor.similarity.core.model.VectorModelCreator;
import com.koval.jresolver.processor.similarity.core.model.VectorModelSerializer;
import com.koval.jresolver.report.HtmlReportGenerator;


public final class LaunchUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(LaunchUtil.class);

  private LaunchUtil() {
  }

  public static void createDataSet() {
    JiraConnectorProperties connectorProperties = new JiraConnectorProperties();
    SimilarityProcessorProperties similarityProcessorProperties = new SimilarityProcessorProperties();
    try (IssueClient jiraClient = getJiraClientInstance(connectorProperties)) {
      JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
      IssueReceiver receiver = jiraConnector.getResolvedIssuesReceiver();
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
    JiraConnectorProperties connectorProperties = new JiraConnectorProperties();
    SimilarityProcessorProperties similarityProcessorProperties = new SimilarityProcessorProperties();
    List<IssueAnalysingResult> results = new ArrayList<>();
    try (IssueClient jiraClient = getJiraClientInstance(connectorProperties);
         RuleEngine ruleEngine = new DroolsRuleEngine()) {
      JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
      IssueReceiver receiver = jiraConnector.getUnresolvedIssuesReceiver();
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
    JiraConnectorProperties connectorProperties = new JiraConnectorProperties();
    try (IssueClient jiraClient = getJiraClientInstance(connectorProperties)) {
      List<IssueField> fields = jiraClient.getIssueFields();
      if (fields.isEmpty()) {
        LOGGER.info("Not exists available fields.");
      } else {
        LOGGER.info("Available fields:");
        fields.forEach(field -> LOGGER.info("Field '{}' with id {}", field.getName(), field.getId()));
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

  private static IssueClient getJiraClientInstance(JiraConnectorProperties connectorProperties) {
    JiraIssueClientFactory jiraIssueClientFactory = new JiraIssueClientFactory();
    IssueClient jiraClient;
    try {
      if (connectorProperties.isAnonymous()) {
        jiraClient = jiraIssueClientFactory.getAnonymousClient(connectorProperties.getUrl());
      } else {
        CredentialsProtector protector = new CredentialsProtector();
        CredentialsKeeper keeper = new CredentialsKeeper(protector, connectorProperties.getCredentialsFolder());
        Credentials credentials;
        if (keeper.isStored()) {
          credentials = keeper.load();
        } else {
          String username = CommandLineUtil.getUsername();
          String password = CommandLineUtil.getPassword();
          credentials = new Credentials(username, password);
          keeper.store(credentials);
        }
        jiraClient = jiraIssueClientFactory.getBasicClient(connectorProperties.getUrl(), credentials);
      }
    } catch (JiraConnectorException e) {
      throw new ResolverException("Could not initialize Jira client.", e);
    }
    return jiraClient;
  }
}
