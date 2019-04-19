package com.koval.jresolver.reporter.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.reporter.ReportGenerator;


public class HtmlReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

  public HtmlReportGenerator() throws IOException {
    FileUtils.forceMkdir(new File("../output"));
  }

  @Override
  public void generate(List<IssueAnalysingResult> results) {
    try {
      fillTemplate(results);
    } catch (IOException e) {
      LOGGER.error("Could not generate report", e);
    }
  }

  private void fillTemplate(List<IssueAnalysingResult> results) throws IOException {
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
