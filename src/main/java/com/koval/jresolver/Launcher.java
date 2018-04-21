package com.koval.jresolver;

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
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    classifier = new DocClassifier(classifierProperties);
    reportGenerator = new HtmlReportGenerator(classifier, new DroolsRuleEngine());

    if (args.length == 0) {
      LOGGER.info("Use default argument 'run'");
      run();
    } else if (args.length == 1) {
      switch (args[0]) {
        case "prepare":
          prepare();
          break;
        case "configure":
          configure();
          break;
        case "run":
          run();
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'configure' or 'run'");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'configure' or 'run'");
    }
  }

  private static void prepare() throws URISyntaxException, IOException {
    LOGGER.info("Preparation...");
    classifier.prepare();
  }

  private static void configure() throws URISyntaxException, IOException {
    LOGGER.info("Configuration...");
    if (checkVectorModelFileNotExists()) {
      LOGGER.error("There're no VectorModel.zip dile");
      return;
    }
    classifier.configure();
    reportGenerator.configure();
  }

  private static void run() throws Exception {
    LOGGER.info("Generation...");
    if (checkDroolsFileNotExists()) {
      LOGGER.error("There're no *.drl files");
      return;
    }
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);
    reportGenerator.generate(jiraConnector.getActualIssues());
  }

  private static boolean checkVectorModelFileNotExists() {
    URL vectorModelResource = Launcher.class.getClassLoader().getResource("VectorModel.zip");
    return vectorModelResource == null;
  }

  private static boolean checkDroolsFileNotExists() throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader().getClass().getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
    Resource[] resources = resolver.getResources("classpath*:*.drl");
    return resources.length == 0;
  }
}
