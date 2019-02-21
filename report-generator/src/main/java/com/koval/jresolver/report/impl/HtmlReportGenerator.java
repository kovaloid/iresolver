package com.koval.jresolver.report.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.processor.IssueProcessingResult;
import com.koval.jresolver.report.ReportGenerator;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

  public HtmlReportGenerator() {
    configure();
  }

  private void configure() {
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
      LOGGER.error("Could not generate report", e);
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
