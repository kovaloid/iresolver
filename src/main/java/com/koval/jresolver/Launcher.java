package com.koval.jresolver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.JiraConnector;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsKeeper;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsProtector;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.processor.ProcessExecutor;
import com.koval.jresolver.processor.result.IssueProcessingResult;
import com.koval.jresolver.processor.rules.RuleEngineProcessor;
import com.koval.jresolver.processor.similarity.SimilarityProcessor;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.core.dataset.DataSetCreator;
import com.koval.jresolver.processor.similarity.core.model.VectorModel;
import com.koval.jresolver.processor.similarity.core.model.VectorModelCreator;
import com.koval.jresolver.processor.similarity.core.model.VectorModelSerializer;
import com.koval.jresolver.report.ReportGenerator;
import com.koval.jresolver.report.impl.HtmlReportGenerator;
import com.koval.jresolver.util.CommandLineUtil;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static JiraConnector jiraConnector;
  private static SimilarityProcessorProperties similarityProcessorProperties;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {
    ConnectorProperties connectorProperties = new ConnectorProperties();
    JiraClient jiraClient;
    if (connectorProperties.isAnonymous()) {
      jiraClient = new BasicJiraClient(connectorProperties.getUrl());
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
      jiraClient = new BasicJiraClient(connectorProperties.getUrl(), credentials);
    }
    jiraConnector = new JiraConnector(jiraClient, connectorProperties);
    similarityProcessorProperties = new SimilarityProcessorProperties();

    if (args.length == 0) {
      LOGGER.info("There are no arguments. Phase 'run' will be started.");
      run();
    } else if (args.length == 1) {
      switch (args[0]) {
        case "prepare":
          LOGGER.info("Classifier preparation phase started.");
          prepare();
          break;
        case "configure":
          LOGGER.info("Classifier and report configuration phase started.");
          configure();
          break;
        case "run":
          LOGGER.info("Report generation phase started.");
          run();
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'prepare', 'configure' or 'run'.");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'prepare', 'configure' or 'run'.");
    }
  }

  private static void prepare() {
    DataSetCreator dataSetCreator = new DataSetCreator(jiraConnector.getResolvedIssuesReceiver(), similarityProcessorProperties);
    try {
      dataSetCreator.create();
    } catch (IOException e) {
      LOGGER.error("Could not create data set", e);
    }
  }

  private static void configure() {
    VectorModelCreator vectorModelCreator = new VectorModelCreator(similarityProcessorProperties);
    File dataSetFile = new File(similarityProcessorProperties.getWorkFolder(), similarityProcessorProperties.getDataSetFileName());
    try {
      VectorModel vectorModel = vectorModelCreator.createFromFile(dataSetFile);
      VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(similarityProcessorProperties);
      vectorModelSerializer.serialize(vectorModel);
    } catch (IOException e) {
      LOGGER.error("There are no " + dataSetFile.getAbsolutePath() + " file. Run 'prepare' phase.", e);
    }
  }

  private static void run() throws IOException, URISyntaxException {
    IssuesReceiver receiver = jiraConnector.getUnresolvedIssuesReceiver();

    ProcessExecutor executor = new ProcessExecutor()
        .add(new SimilarityProcessor(similarityProcessorProperties))
        .add(new RuleEngineProcessor());
    Collection<IssueProcessingResult> results = new ArrayList<>();

    while (receiver.hasNextIssues()) {
      results.addAll(executor.execute(receiver.getNextIssues()));
    }

    ReportGenerator generator = new HtmlReportGenerator();
    generator.generate(results);
  }
}
