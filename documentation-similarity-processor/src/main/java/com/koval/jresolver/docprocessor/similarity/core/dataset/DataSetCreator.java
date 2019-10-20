package com.koval.jresolver.docprocessor.similarity.core.dataset;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.docprocessor.similarity.configuration.DocumentationSimilarityProcessorProperties;


public class DataSetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSetCreator.class);
  private static final String SEPARATOR = " | ";

  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final IssueReceiver receiver;
  private final DocumentationSimilarityProcessorProperties properties;

  public DataSetCreator(IssueReceiver receiver, DocumentationSimilarityProcessorProperties properties) {
    this.receiver = receiver;
    this.properties = properties;
  }

  public void create() throws IOException { /*TODO decide how to use it in documentation*/
    File dataSetFile = new File(properties.getWorkFolder(), properties.getDataSetFileName());
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getCanonicalPath());
    LOGGER.info("Start creating data set file: {}", dataSetFile.getName());
    try (PrintWriter output = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name())) {
      while (receiver.hasNextIssues()) {
        Collection<Issue> issues = receiver.getNextIssues();
        issues.forEach(issue -> {
          String textData = textDataExtractor.extract(issue);
          if (textData.isEmpty()) {
            LOGGER.info("Document with key {} was ignored due to empty body", issue.getKey());
          } else {
            output.print(issue.getKey());
            output.print(SEPARATOR);
            output.println(textData);
            LOGGER.info("Document with key {} was added to data set", issue.getKey());
          }
        });
        output.flush();
      }
    }
    LOGGER.info("Data set file was created: {}", dataSetFile.getCanonicalPath());
  }
}
