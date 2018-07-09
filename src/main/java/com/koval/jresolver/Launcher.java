package com.koval.jresolver;

import java.io.*;

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
import com.koval.jresolver.manager.Manager;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.core.impl.HtmlReportGenerator;
import com.koval.jresolver.rules.core.impl.DroolsRuleEngine;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static Classifier classifier;
  private static ReportGenerator reportGenerator;

  private static boolean prepare;
  private static boolean configure;
  private static boolean run;
  private static boolean password;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {

    for (String arg : args) {
      switch (arg) {
        case "prepare":
          LOGGER.info("+prepare");
          prepare = true;
          break;
        case "configure":
          LOGGER.info("+configure");
          configure = true;
          break;
        case "run":
          LOGGER.info("+run");
          run = true;
          break;
        case "password":
          LOGGER.info("+password");
          password = true;
          break;
        default:
          LOGGER.warn("undefined arg={}, skipped.", arg);
          break;
      }
    }

    if (prepare) {
      prepare();
    }
    if (configure) {
      configure();
    }
    if (run) {
      run();
    }

  }

  private static void prepare() throws Exception {
    if (!checkDataSetFileNotExists()) {
      LOGGER.info("Prepare stage skipped.");
      return;
    }
    LOGGER.info("Prepare stage started!");
    createClassifier();
    classifier.prepare();
    LOGGER.info("Prepare stage completed!");
  }

  private static void configure() throws Exception {
    if (checkDataSetFileNotExists()) {
      LOGGER.error("There are no 'DataSet.txt' file in 'data' folder. Run 'prepare' phase.");
      return;
    }

    if ((!checkVectorModelFileNotExists()) && checkReportGeneratorConfiguration()) {
      LOGGER.info("Configuration stage skipped.");
      return;
    }

    LOGGER.info("Configuration stage started!");
    if (classifier == null) {
      createClassifier();
    }
    classifier.configure();
    if (reportGenerator == null) {
      createReportGenerator();
    }
    reportGenerator.configure();
    LOGGER.info("Configuration stage completed!");
  }

  private static void run() throws Exception {
    if (checkVectorModelFileNotExists()) {
      LOGGER.error("There are no 'VectorModel.zip' file in 'data' folder. Run 'configure' phase.");
      return;
    }

    LOGGER.info("Run stage started!");

    if (checkDroolsFileNotExists()) {
      LOGGER.error("There are no '*.drl' files in 'rules' folder. Add '*.drl' files.");
      return;
    }
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    JiraConnector jiraConnector = new JiraConnector(jiraProperties);
    if (reportGenerator == null) {
      createReportGenerator();
    }
    reportGenerator.generate(jiraConnector.getActualIssues());
    LOGGER.info("Run stage completed!");
  }

  private static String getPassword() throws Exception {
    Console console = System.console();
    if (console == null) {
      BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
      return buffer.readLine();
    }
    return String.valueOf(console.readPassword("Enter your Jira password: "));
  }

  private static boolean checkDataSetFileNotExists() {
    return !Manager.getDataSet().exists();
  }

  private static boolean checkVectorModelFileNotExists() {
    return !Manager.getVectorModel().exists();
  }

  private static boolean checkDroolsFileNotExists() throws IOException {
    ClassLoader classLoader = Launcher.class.getClassLoader();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
    Resource[] resources = resolver.getResources("classpath*:*.drl");
    return resources.length == 0;
  }

  private static boolean checkReportGeneratorConfiguration() {
    return new File("../output").exists();
  }

  private static void createClassifier() throws Exception {
    String pass = null;
    if (password) {
      pass = getPassword();
    }

    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    if (pass == null || pass.isEmpty()) {
      classifier = new DocClassifier(classifierProperties);
    } else {
      classifier = new DocClassifier(classifierProperties, String.valueOf(password));
    }
  }

  private static void createReportGenerator() throws Exception {
    if (classifier == null) {
      createClassifier();
    }
    reportGenerator = new HtmlReportGenerator(classifier, new DroolsRuleEngine());
  }
}