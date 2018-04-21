package com.koval.jresolver;

import java.io.Console;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static Classifier classifier;
  private static ReportGenerator reportGenerator;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {
    char[] password = getPassword();
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    if (password == null || password.length == 0) {
      classifier = new DocClassifier(classifierProperties);
    } else {
      classifier = new DocClassifier(classifierProperties, String.valueOf(password));
    }
    reportGenerator = new HtmlReportGenerator(classifier, new DroolsRuleEngine());

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
    classifier.prepare();
  }

  private static void configure() throws URISyntaxException, IOException {
    if (checkDataSetFileNotExists()) {
      LOGGER.error("There are no 'DataSet.txt' file in 'data' folder. Run 'prepare' phase.");
      return;
    }
    classifier.configure();
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
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);
    reportGenerator.generate(jiraConnector.getActualIssues());
  }

  private static char[] getPassword() {
    Console console = System.console();
    if (console == null) {
      LOGGER.error("Could not get console instance.");
      System.exit(0);
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
