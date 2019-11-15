package com.koval.jresolver.reporter.html;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.reporter.ReportGenerator;
import com.koval.jresolver.common.api.constant.ProcessorConstants;
import com.koval.jresolver.reporter.html.configuration.HtmlReporterConfiguration;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);
  private static final String REPORT_FILE_NAME = "index.html";

  private final HtmlReporterConfiguration configuration;
  private final List<String> enabledProcessors;

  public HtmlReportGenerator(HtmlReporterConfiguration configuration, List<String> enabledProcessors) throws IOException {
    this.configuration = configuration;
    this.enabledProcessors = enabledProcessors;
    FileUtils.forceMkdir(new File(configuration.getOutputFolder()));
  }

  @Override
  public void generate(List<IssueAnalysingResult> results) {
    try {
      fillTemplate(results);
      if (configuration.isOpenBrowser()) {
        openReport();
      }
    } catch (IOException e) {
      LOGGER.error("Could not generate report", e);
    }
  }

  private void fillTemplate(List<IssueAnalysingResult> results) throws IOException {
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    velocityEngine.init();
    Template template = velocityEngine.getTemplate("html-report-template.vm");
    VelocityContext context = new VelocityContext();
    context.put("results", results);
    context.put("numberTool", new NumberTool());
    context.put("isSimilarityProcessorEnabled", enabledProcessors.contains(ProcessorConstants.SIMILARITY));
    context.put("isDocumentationProcessorEnabled", enabledProcessors.contains(ProcessorConstants.DOCUMENTATION));
    context.put("isRuleEngineProcessorEnabled", enabledProcessors.contains(ProcessorConstants.RULE_ENGINE));
    try (StringWriter writer = new StringWriter()) {
      template.merge(context, writer);
      LOGGER.debug(writer.toString());
    }
    try (OutputStream outputStream = new FileOutputStream(new File(configuration.getOutputFolder(), REPORT_FILE_NAME));
         Writer fileWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
      template.merge(context, fileWriter);
    }
  }

  private void openReport() throws IOException {
    File file = new File(configuration.getOutputFolder(), REPORT_FILE_NAME);
    try {
      Desktop.getDesktop().browse(getUriFromFile(file));
    } catch (UnsupportedOperationException e) {
      LOGGER.warn("Could not open browser on the current platform", e);
    }
  }

  private URI getUriFromFile(File file) throws IOException {
    String prefix = "file:///";
    String path = file.getCanonicalPath().replace('\\', '/');
    return URI.create(prefix + path);
  }
}
