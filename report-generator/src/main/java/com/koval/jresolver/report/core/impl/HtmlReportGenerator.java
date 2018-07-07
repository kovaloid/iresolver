package com.koval.jresolver.report.core.impl;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.results.ClassifierResult;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.manager.Manager;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.results.TotalResult;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.results.RulesResult;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

  private final Classifier classifier;
  private final RuleEngine ruleEngine;

  public HtmlReportGenerator(Classifier classifier, RuleEngine ruleEngine) {
    this.classifier = classifier;
    this.ruleEngine = ruleEngine;
  }

  @Override
  public void configure() {
    File file = new File(Manager.getOutputDirectory());
    if (file.exists()) {
      LOGGER.info("Report output folder is already exists.");
    } else {
      if (file.mkdir()) {
        LOGGER.info("Report output folder was created.");
      } else {
        throw new RuntimeException("Could not create 'output' folder.");
      }
    }
  }

  @Override
  public void generate(List<JiraIssue> actualIssues) throws IOException, URISyntaxException {
    List<TotalResult> results = new ArrayList<>();
    for (JiraIssue actualIssue : actualIssues) {
      ClassifierResult classifierResult = classifier.execute(actualIssue, Manager.getDataDirectory() + "VectorModel.zip");
      RulesResult ruleResult = ruleEngine.execute(actualIssue);
      TotalResult totalResult = new TotalResult(actualIssue, classifierResult, ruleResult);
      results.add(totalResult);
    }
    fillTemplate(results);
  }

  private void fillTemplate(List<TotalResult> results) throws IOException {
    VelocityEngine velocityEngine = new VelocityEngine();
    Properties props = new Properties();
    props.put("file.resource.loader.path", Manager.getConfigDirectory());
    velocityEngine.init(props);
    Template template = velocityEngine.getTemplate("template.vm");
    VelocityContext context = new VelocityContext();
    context.put("results", results);
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    LOGGER.debug(writer.toString());
    try (OutputStream outputStream = new FileOutputStream(Manager.getOutputDirectory() + "index.html");
         Writer fileWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
      template.merge(context, fileWriter);
    }
    LOGGER.info("===================================");
    LOGGER.info("Completed! Check /output/index.html");
  }
}
