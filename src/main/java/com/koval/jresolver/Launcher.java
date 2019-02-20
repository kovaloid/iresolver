package com.koval.jresolver;

import java.io.Console;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import com.koval.jresolver.connector.jira.JiraConnector;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsKeeper;
import com.koval.jresolver.connector.jira.configuration.auth.CredentialsProtector;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.connector.jira.util.CommandLineUtil;
import com.koval.jresolver.processor.IssueProcessingResult;
import com.koval.jresolver.processor.ProcessExecutor;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.SimilarityProcessor;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.rules.RuleEngineProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.koval.jresolver.report.core.ReportGenerator;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static ReportGenerator reportGenerator;

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
    JiraConnector jiraConnector = new JiraConnector(jiraClient, connectorProperties);
    IssuesReceiver receiver = jiraConnector.getUnresolvedIssuesReceiver();

    ProcessExecutor executor = new ProcessExecutor()
        .add(new SimilarityProcessor(new SimilarityProcessorProperties()))
        .add(new RuleEngineProcessor());
    Collection<IssueProcessingResult> results = new ArrayList<>();

    while (receiver.hasNextIssues()) {
      results.addAll(executor.execute(receiver.getNextIssues()));
    }

    ReportGenerator generator = new HtmlReportGenerator();
    generator.generate(results);




    // ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    // classifier = new DocClassifier(classifierProperties);
    // reportGenerator = new HtmlReportGenerator(classifier, new DroolsRuleEngine());

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

  private static void prepare() throws URISyntaxException, IOException {
    // classifier.prepare();
  }

  private static void configure() throws URISyntaxException, IOException {
    if (checkDataSetFileNotExists()) {
      LOGGER.error("There are no 'DataSet.txt' file in 'data' folder. Run 'prepare' phase.");
      return;
    }
    // classifier.configure();
    reportGenerator.configure();
  }

  private static void run() throws Exception {
    if (checkVectorModelFileNotExists()) {
      LOGGER.error("There are no 'VectorModel.zip' file in 'data' folder. Run 'configure' phase.");
      return;
    }
    if (checkDroolsFileNotExists()) {
      LOGGER.error("There are no '*.drl' files in 'rules' folder. Add '*.drl' files.");
      return;
    }
    //ConnectorProperties connectorProperties = new ConnectorProperties();
    //JiraConnector jiraConnector = new JiraConnector(connectorProperties);
    //reportGenerator.generate(jiraConnector.getUnresolvedIssues());
  }

  private static char[] getPassword() {
    Console console = System.console();
    if (console == null) {
      throw new RuntimeException("Could not get console instance.");
    }
    return console.readPassword("Enter your Jira password: ");
  }

  private static boolean checkDataSetFileNotExists() {
    URL dataSetResource = Launcher.class.getClassLoader().getResource("DataSet.txt");
    return dataSetResource == null;
  }

  private static boolean checkVectorModelFileNotExists() {
    URL vectorModelResource = Launcher.class.getClassLoader().getResource("VectorModel.zip");
    return vectorModelResource == null;
  }

  private static boolean checkDroolsFileNotExists() throws IOException {
    ClassLoader classLoader = Launcher.class.getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
    Resource[] resources = resolver.getResources("classpath*:*.drl");
    return resources.length == 0;
  }
}
