package com.koval.resolver.processor.issues.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.configuration.bean.processors.IssuesProcessorConfiguration;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;


public class IssuesDataSetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(IssuesDataSetCreator.class);
  private static final String SEPARATOR = " | ";

  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final IssueReceiver receiver;
  private final IssuesProcessorConfiguration properties;

  public IssuesDataSetCreator(final IssueReceiver receiver, final IssuesProcessorConfiguration properties) {
    this.receiver = receiver;
    this.properties = properties;
  }

  public void create() throws IOException {
    final File dataSetFile = new File(properties.getDataSetFile());
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getCanonicalPath());
    if (properties.isOverwriteMode()) {
      LOGGER.info("Start creating data set file: {}", dataSetFile.getName());
      try (PrintWriter output = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name());
           BufferedWriter writer = new BufferedWriter(output)) {
        writeIssues(output, writer);
      }
      LOGGER.info("Data set file was created: {}", dataSetFile.getCanonicalPath());
    } else {
      LOGGER.info("Start appending data set file: {}", dataSetFile.getName());
      try (FileOutputStream fos = new FileOutputStream(dataSetFile, true);
           OutputStreamWriter output = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
           BufferedWriter writer = new BufferedWriter(output)) {
        writer.newLine();
        writeIssues(output, writer);
      }
      LOGGER.info("Data set file was updated: {}", dataSetFile.getCanonicalPath());
    }
  }

  private void writeIssues(Writer output, BufferedWriter writer) throws IOException {
    while (receiver.hasNextIssues()) {
      final Collection<Issue> issues = receiver.getNextIssues();
      for (final Issue issue : issues) {
        final String textData = textDataExtractor.extract(issue);
        if (textData.isEmpty()) {
          LOGGER.info("Issue with key {} was ignored due to empty body", issue.getKey());
        } else {
          writer.write(issue.getKey());
          writer.write(SEPARATOR);
          writer.write(textData);
          writer.newLine();
          LOGGER.info("Issue with key {} was added to data set", issue.getKey());
        }
      }
      output.flush();
    }
  }
}
