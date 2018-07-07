package com.koval.jresolver;

import java.io.*;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  static {
    PropertyConfigurator.configure(Manager.getConfigDirectory() + "log4j.properties");
  }

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

//    if (args.length == 0) {
//      LOGGER.info("There are no arguments. Phase 'run' will be started.");
//      run = true;
//    } //cyclomatic complexity too high for it :C

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
    if (Manager.checkClassifierPrepare()) {
      LOGGER.info("Skip classifier preparation.");
      return;
    }
    createClassifier();
    classifier.prepare();
  }

  private static void configure() throws Exception {
    if (checkDataSetFileNotExists()) {
      LOGGER.error("There are no 'DataSet.txt' file in 'data' folder. Run 'prepare' phase.");
      return;
    }

    if (Manager.checkClassifierConfigure() && Manager.checkReportGeneratorConfigure()) {
      LOGGER.info("Skip configuration stage.");
      return;
    }

    if (classifier == null) {
      createClassifier();
    }
    classifier.configure();

    if (reportGenerator == null) {
      createReportGenerator();
    }
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
    if (reportGenerator == null) {
      createReportGenerator();
    }
    reportGenerator.generate(jiraConnector.getActualIssues());
  }

  private static String getPassword() throws Exception {
    Console console = System.console();
    if (console == null) {
      //throw new RuntimeException("Could not get console instance.");
      BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
      return buffer.readLine();
    }
    return String.valueOf(console.readPassword("Enter your Jira password: "));
  }

  private static boolean checkDataSetFileNotExists() {
    return !new File(Manager.getDataDirectory() + "DataSet.txt").exists();
  }

  private static boolean checkVectorModelFileNotExists() {
    return !new File(Manager.getDataDirectory() + "VectorModel.zip").exists();
  }

  private static boolean checkDroolsFileNotExists() {
    File dir = new File(Manager.getRulesDirectory());
    String[] files = dir.list();
    return files == null || files.length == 0;
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
