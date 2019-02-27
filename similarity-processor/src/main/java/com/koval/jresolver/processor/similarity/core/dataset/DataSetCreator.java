package com.koval.jresolver.processor.similarity.core.dataset;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;


public class DataSetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSetCreator.class);
  private static final String SEPARATOR = " | ";

  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final IssuesReceiver receiver;
  private final SimilarityProcessorProperties properties;

  public DataSetCreator(IssuesReceiver receiver, SimilarityProcessorProperties properties) {
    this.receiver = receiver;
    this.properties = properties;
  }

  public void create() throws IOException {
    File dataSetFile = new File(properties.getWorkFolder(), properties.getDataSetFileName());
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getAbsolutePath());
    LOGGER.info("Start creating data set file: {}", dataSetFile.getName());
    try (PrintWriter output = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name())) {
      while (receiver.hasNextIssues()) {
        Collection<Issue> issues = receiver.getNextIssues();
        issues.forEach(issue -> {
          String textData = textDataExtractor.extract(issue);
          if (textData.isEmpty()) {
            LOGGER.info("Issue with key {} was ignored due to empty body", issue.getKey());
          } else {
            output.print(issue.getKey());
            output.print(SEPARATOR);
            output.println(textData);
            LOGGER.info("Issue with key {} was added to data set", issue.getKey());
          }
        });
        output.flush();
      }
    }
    LOGGER.info("Data set file was created: {}", dataSetFile.getAbsolutePath());
  }
}
