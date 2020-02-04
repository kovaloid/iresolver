package com.koval.jresolver.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.ProcessExecutor;
import com.koval.jresolver.common.api.auth.Credentials;
import com.koval.jresolver.common.api.auth.CredentialsKeeper;
import com.koval.jresolver.common.api.auth.CredentialsProtector;
import com.koval.jresolver.common.api.bean.confluence.ConfluencePage;
import com.koval.jresolver.common.api.bean.issue.IssueField;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.common.api.component.processor.DataSetWriter;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.common.api.component.reporter.ReportGenerator;
import com.koval.jresolver.common.api.configuration.Configuration;
import com.koval.jresolver.common.api.configuration.bean.connectors.BugzillaConnectorConfiguration;
import com.koval.jresolver.common.api.configuration.bean.connectors.JiraConnectorConfiguration;
import com.koval.jresolver.common.api.constant.ConnectorConstants;
import com.koval.jresolver.common.api.constant.ProcessorConstants;
import com.koval.jresolver.common.api.constant.ReporterConstants;
import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelCreator;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.jresolver.connector.bugzilla.BugzillaConnector;
import com.koval.jresolver.connector.bugzilla.client.BugzillaIssueClientFactory;
import com.koval.jresolver.connector.bugzilla.exception.BugzillaConnectorException;
import com.koval.jresolver.connector.confluence.ConfluenceConnector;
import com.koval.jresolver.connector.jira.JiraConnector;
import com.koval.jresolver.connector.jira.client.JiraIssueClientFactory;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;
import com.koval.jresolver.exception.ResolverException;
import com.koval.jresolver.processor.confluence.ConfluenceProcessor;
import com.koval.jresolver.processor.confluence.core.ConfluenceDataSetWriter;
import com.koval.jresolver.processor.documentation.DocumentationProcessor;
import com.koval.jresolver.processor.documentation.core.DocDataSetCreator;
import com.koval.jresolver.processor.issues.IssuesProcessor;
import com.koval.jresolver.processor.issues.core.DataSetCreator;
import com.koval.jresolver.processor.issues.test.TestSimilarityProcessor;
import com.koval.jresolver.processor.rules.RuleEngineProcessor;
import com.koval.jresolver.reporter.html.HtmlReportGenerator;
import com.koval.jresolver.reporter.text.TextReportGenerator;
import com.koval.jresolver.util.CommandLineUtil;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private final Configuration configuration;

  public Launcher(Configuration configuration) {
    this.configuration = configuration;
  }

  public void createIssuesDataSet() {
    try (IssueClient issueClient = getIssueClient()) {
      Connector connector = getConnector(issueClient);
      IssueReceiver receiver = connector.getResolvedIssuesReceiver();
      DataSetCreator dataSetCreator = new DataSetCreator(receiver, configuration.getProcessors().getIssues());
      dataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create data set file.", e);
    }
  }

  public void createIssuesVectorModel() {
    String dataSetFile = configuration.getProcessors().getIssues().getDataSetFile();
    String vectorModelFile = configuration.getProcessors().getIssues().getVectorModelFile();
    createVectorModel(dataSetFile, vectorModelFile, "Could not create issues vector model file.");
  }

  public void createDocumentationDataSet() {
    DocDataSetCreator docDataSetCreator = new DocDataSetCreator(configuration.getProcessors().getDocumentation());
    docDataSetCreator.convertWordFilesToPdf();
    try {
      docDataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create documentation data set file.", e);
    }
  }

  public void createDocumentationVectorModel() {
    String dataSetFile = configuration.getProcessors().getDocumentation().getDataSetFile();
    String vectorModelFile = configuration.getProcessors().getDocumentation().getVectorModelFile();
    createVectorModel(dataSetFile, vectorModelFile, "Could not create documentation vector model file.");
  }

  public void createConfluenceDataSet() {
    ConfluenceConnector confluenceConnector = new ConfluenceConnector(configuration.getConnectors().getConfluence());
    try (DataSetWriter<ConfluencePage> confluenceDataSetWriter = new ConfluenceDataSetWriter(configuration.getProcessors().getConfluence())) {
      confluenceConnector.createDataSet(confluenceDataSetWriter);
    } catch (IOException e) {
      LOGGER.error("Could not create confluence data set file.", e);
    }
  }

  public void createConfluenceVectorModel() {
    String dataSetFile = configuration.getProcessors().getConfluence().getDataSetFile();
    String vectorModelFile = configuration.getProcessors().getConfluence().getVectorModelFile();
    createVectorModel(dataSetFile, vectorModelFile, "Could not create confluence vector model file.");
  }

  private void createVectorModel(String dataSetFile, String vectorModelFile, String errorMessage) {
    VectorModelCreator vectorModelCreator = new VectorModelCreator(configuration.getParagraphVectors());
    try {
      VectorModel vectorModel = vectorModelCreator.createFromFile(new File(dataSetFile));
      VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
      vectorModelSerializer.serialize(vectorModel, vectorModelFile);
    } catch (IOException e) {
      LOGGER.error(errorMessage, e);
    }
  }

  public void run() {
    List<IssueAnalysingResult> results = new ArrayList<>();
    try (IssueClient issueClient = getIssueClient()) {
      Connector connector = getConnector(issueClient);
      IssueReceiver receiver = connector.getUnresolvedIssuesReceiver();
      ProcessExecutor executor = new ProcessExecutor();
      for (IssueProcessor issueProcessor : getIssueProcessors(issueClient)) {
        executor.add(issueProcessor);
      }
      if (configuration.getAdministration().isParallelExecution()) {
        while (receiver.hasNextIssues()) {
          results.addAll(executor.parallelExecute(receiver.getNextIssues()));
        }
      } else {
        while (receiver.hasNextIssues()) {
          results.addAll(executor.execute(receiver.getNextIssues()));
        }
      }
    } catch (IOException e) {
      LOGGER.error("Could not run issues processing.", e);
    }
    try {
      for (ReportGenerator reportGenerator : getReportGenerators()) {
        reportGenerator.generate(results);
      }
    } catch (IOException e) {
      LOGGER.error("Could not initialize report generator.", e);
    }
  }

  private List<IssueProcessor> getIssueProcessors(IssueClient issueClient) throws IOException {
    List<String> processorNames = configuration.getAdministration().getProcessors();
    List<IssueProcessor> issueProcessors = new ArrayList<>();
    if (processorNames.contains(ProcessorConstants.ISSUES)) {
      issueProcessors.add(new IssuesProcessor(issueClient, configuration));
    }
    if (processorNames.contains(ProcessorConstants.DOCUMENTATION)) {
      issueProcessors.add(new DocumentationProcessor(configuration));
    }
    if (processorNames.contains(ProcessorConstants.CONFLUENCE)) {
      issueProcessors.add(new ConfluenceProcessor(configuration));
    }
    if (processorNames.contains(ProcessorConstants.RULE_ENGINE)) {
      issueProcessors.add(new RuleEngineProcessor(configuration));
    }
    if (issueProcessors.isEmpty()) {
      LOGGER.warn("Could not find any appropriate issue processor in the list: {}", processorNames);
    }
    return issueProcessors;
  }

  private List<ReportGenerator> getReportGenerators() throws IOException {
    List<String> reporterNames = configuration.getAdministration().getReporters();
    List<ReportGenerator> reportGenerators = new ArrayList<>();
    if (reporterNames.contains(ReporterConstants.HTML)) {
      reportGenerators.add(new HtmlReportGenerator(configuration.getReporters().getHtml(), configuration.getAdministration().getProcessors()));
    }
    if (reporterNames.contains(ReporterConstants.TEXT)) {
      reportGenerators.add(new TextReportGenerator(configuration.getReporters().getText()));
    }
    if (reportGenerators.isEmpty()) {
      LOGGER.warn("Could not find any appropriate report generator in the list: {}", reporterNames);
    }
    return reportGenerators;
  }

  public void printIssueFields() {
    try (IssueClient issueClient = getIssueClient()) {
      List<IssueField> fields = issueClient.getIssueFields();
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

  public void runUI() {
    new LauncherUI(this);
  }

  public void testSimilarityProcessor() {
    try {
      new TestSimilarityProcessor().test();
    } catch (IOException e) {
      LOGGER.error("Could not perform similarity processor testing...", e);
    }
  }

  private Connector getConnector(IssueClient issueClient) {
    String connectorName = configuration.getAdministration().getConnector();
    if (ConnectorConstants.JIRA.equalsIgnoreCase(connectorName)) {
      return new JiraConnector(issueClient, configuration.getConnectors().getJira());
    } else if (ConnectorConstants.BUGZILLA.equalsIgnoreCase(connectorName)) {
      return new BugzillaConnector(issueClient, configuration.getConnectors().getBugzilla());
    } else {
      throw new ResolverException("Could not get connector with name: " + connectorName);
    }
  }

  private IssueClient getIssueClient() {
    String connectorName = configuration.getAdministration().getConnector();
    if (ConnectorConstants.JIRA.equalsIgnoreCase(connectorName)) {
      return getJiraClientInstance();
    } else if (ConnectorConstants.BUGZILLA.equalsIgnoreCase(connectorName)) {
      return getBugzillaClientInstance();
    } else {
      throw new ResolverException("Could not get issue client for connector with name: " + connectorName);
    }
  }

  private IssueClient getJiraClientInstance() {
    JiraConnectorConfiguration connectorConfiguration = configuration.getConnectors().getJira();
    JiraIssueClientFactory jiraIssueClientFactory = new JiraIssueClientFactory();
    IssueClient jiraClient;
    try {
      if (connectorConfiguration.isAnonymous()) {
        jiraClient = jiraIssueClientFactory.getAnonymousClient(connectorConfiguration.getUrl());
      } else {
        Credentials credentials = getCredentials(connectorConfiguration.getCredentialsFolder());
        jiraClient = jiraIssueClientFactory.getBasicClient(connectorConfiguration.getUrl(), credentials);
      }
    } catch (JiraConnectorException e) {
      throw new ResolverException("Could not initialize Jira client.", e);
    }
    return jiraClient;
  }

  private IssueClient getBugzillaClientInstance() {
    BugzillaConnectorConfiguration connectorConfiguration = configuration.getConnectors().getBugzilla();
    BugzillaIssueClientFactory bugzillaIssueClientFactory = new BugzillaIssueClientFactory();
    IssueClient bugzillaClient;
    try {
      if (connectorConfiguration.isAnonymous()) {
        bugzillaClient = bugzillaIssueClientFactory.getAnonymousClient(connectorConfiguration.getUrl());
      } else {
        Credentials credentials = getCredentials(connectorConfiguration.getCredentialsFolder());
        bugzillaClient = bugzillaIssueClientFactory.getBasicClient(connectorConfiguration.getUrl(), credentials);
      }
    } catch (BugzillaConnectorException e) {
      throw new ResolverException("Could not initialize Bugzilla client.", e);
    }
    return bugzillaClient;
  }

  private Credentials getCredentials(String credentialsFolder) {
    CredentialsProtector protector = new CredentialsProtector();
    CredentialsKeeper keeper = new CredentialsKeeper(protector, credentialsFolder);
    Credentials credentials;
    if (keeper.isStored()) {
      credentials = keeper.load();
    } else {
      String username = CommandLineUtil.getUsername();
      String password = CommandLineUtil.getPassword();
      credentials = new Credentials(username, password);
      keeper.store(credentials);
    }
    return credentials;
  }
}
