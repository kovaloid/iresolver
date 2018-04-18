package com.koval.jresolver;

import java.net.URISyntaxException;

import com.koval.jresolver.classifier.Classifier;
import com.koval.jresolver.classifier.impl.Doc2vecClassifier;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.report.HtmlReportGenerator;
import com.koval.jresolver.report.ReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("No arguments. Please use 'configure' or 'run'");
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
          System.out.println("Wrong arguments. Please use 'configure' or 'run'");
          break;
      }
    } else {
      System.out.println("Too much arguments. Please use 'configure' or 'run'");
    }
  }

  private static void prepare() throws URISyntaxException {
    LOGGER.info("Preparation...");
    Classifier classifier = new Doc2vecClassifier();
    classifier.prepare();
  }

  private static void configure() throws URISyntaxException {
    LOGGER.info("Configuration...");
    Classifier classifier = new Doc2vecClassifier();
    classifier.configure();
  }

  private static void run() throws Exception {
    System.out.println("Generation...");
    ReportGenerator reportGenerator = new HtmlReportGenerator();
    reportGenerator.generate();


    //BasicJiraClient cl = new BasicJiraClient("https://issues.apache.org/jira");
    //cl.getIssueByKey("AMQ-6134 ");
  }
}
