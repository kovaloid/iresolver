package com.koval.jresolver.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.classifier.impl.Doc2vecClassifier;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.rules.RuleEngine;
import com.koval.jresolver.rules.RulesResult;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static JiraConnector jiraConnector;
  private static Doc2vecClassifier doc2vecClassifier;
  private static RuleEngine ruleEngine;

  private Launcher() {
  }

  public static void main(String[] args) throws Exception {
    ruleEngine = new RuleEngine();
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    jiraConnector = new JiraConnector(jiraProperties);
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    doc2vecClassifier = new Doc2vecClassifier(classifierProperties);
    //configure();
    generate();
    ruleEngine.close();
  }

  private static void fillTemplate(List<TotalResults> results) throws IOException {
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    velocityEngine.init();

    Template template = velocityEngine.getTemplate("index.vm");

    VelocityContext context = new VelocityContext();

    context.put("results", results);

    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    System.out.println(writer.toString());


    try (FileWriter fileWriter = new FileWriter("index.html")) {
      template.merge(context, fileWriter);
    }

  }

  private static void configure() throws IOException {
    doc2vecClassifier.configure();
  }

  private static void generate() throws Exception {
    List<JiraIssue> actualIssues = jiraConnector.getActualIssues();
    LOGGER.info("Retrieving actual issues completed");

    List<TotalResults> res = new ArrayList<>();

    for (JiraIssue actualIssue : actualIssues) {
      ClassifierResult classifierResult = doc2vecClassifier.execute(actualIssue);
      RulesResult ruleResult = ruleEngine.execute(actualIssue);

      TotalResults totalResults = new TotalResults(actualIssue, classifierResult, ruleResult);
      res.add(totalResults);
    }

    fillTemplate(res);
  }

}
