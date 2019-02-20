package com.koval.jresolver.report.core.impl;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.koval.jresolver.processor.IssueProcessingResult;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.deeplearning4j.nn.api.Classifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.report.core.ReportGenerator;
import com.koval.jresolver.report.results.TotalResult;
import com.koval.jresolver.rules.core.RuleEngine;
import com.koval.jresolver.rules.results.RulesResult;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

  @Override
  public void configure() {
    File file = new File("../output");
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
  public void generate(Collection<IssueProcessingResult> results) {
    try {
      fillTemplate(results);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fillTemplate(Collection<IssueProcessingResult> results) throws IOException {
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    velocityEngine.init();
    Template template = velocityEngine.getTemplate("template.vm");
    VelocityContext context = new VelocityContext();
    context.put("results", results);
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    LOGGER.debug(writer.toString());
    try (OutputStream outputStream = new FileOutputStream("../output/index.html");
         Writer fileWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
      template.merge(context, fileWriter);
    }
  }
}
