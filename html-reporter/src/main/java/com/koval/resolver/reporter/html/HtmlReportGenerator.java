package com.koval.resolver.reporter.html;

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

import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.reporter.ReportGenerator;
import com.koval.resolver.common.api.configuration.bean.reporters.HtmlReporterConfiguration;
import com.koval.resolver.common.api.constant.ProcessorConstants;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

  private final HtmlReporterConfiguration configuration;
  private final List<String> enabledProcessors;

  public HtmlReportGenerator(HtmlReporterConfiguration configuration, List<String> enabledProcessors) throws IOException {
    this.configuration = configuration;
    this.enabledProcessors = enabledProcessors;
    FileUtils.forceMkdirParent(new File(configuration.getOutputFile()));
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
    Template template = velocityEngine.getTemplate(configuration.getHtmlTemplateFileName());
    VelocityContext context = new VelocityContext();
    context.put("results", results);
    context.put("numberTool", new NumberTool());
    context.put("isIssuesProcessorEnabled", enabledProcessors.contains(ProcessorConstants.ISSUES));
    context.put("isDocumentationProcessorEnabled", enabledProcessors.contains(ProcessorConstants.DOCUMENTATION));
    context.put("isConfluenceProcessorEnabled", enabledProcessors.contains(ProcessorConstants.CONFLUENCE));
    context.put("isRuleEngineProcessorEnabled", enabledProcessors.contains(ProcessorConstants.RULE_ENGINE));
    try (StringWriter writer = new StringWriter()) {
      template.merge(context, writer);
      LOGGER.debug(writer.toString());
    }
    try (OutputStream outputStream = new FileOutputStream(new File(configuration.getOutputFile()));
         Writer fileWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
      template.merge(context, fileWriter);
    }
  }

  private void openReport() throws IOException {
    File file = new File(configuration.getOutputFile());
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      Desktop.getDesktop().browse(getUriFromFile(file));
      LOGGER.warn("Opening the default browser with report...");
    } else {
      LOGGER.warn("Could not open default browser on the current platform");
      LOGGER.warn("Please open the {} file manually", configuration.getOutputFile());
    }
  }

  private URI getUriFromFile(File file) throws IOException {
    String prefix = "file:///";
    String path = file.getCanonicalPath().replace('\\', '/');
    return URI.create(prefix + path);
  }
}
