package com.koval.jresolver.processor.similarity.core.dataset;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.util.FilesUtil;


public class DataSetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSetCreator.class);
  private static final String SEPARATOR = " | ";

  private final IssuesReceiver receiver;
  private final SimilarityProcessorProperties properties;

  public DataSetCreator(IssuesReceiver receiver, SimilarityProcessorProperties properties) {
    this.receiver = receiver;
    this.properties = properties;
  }

  public void create() {
    File dataSetFile = new File(properties.getWorkFolder(), properties.getDataSetFileName());
    try {
      FilesUtil.createFile(dataSetFile);
    } catch (IOException e) {
      LOGGER.error("Could not create data set file.", e);
    }
    //LOGGER.info("New data set dataSetFile will be created: {}", this.dataSetFile.getAbsolutePath());

    /*File dataSetFile = new File(workFolder, "DataSet.txt");
    if (!appendToDataSet) {
      if (dataSetFile.exists()) {
        dataSetFile.delete();
      }
      dataSetFile.createNewFile();
    }*/

    try (PrintWriter output = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name())) {
      while (receiver.hasNextIssues()) {
        Collection<Issue> issues = receiver.getNextIssues();
        issues.forEach(issue -> {
          output.print(issue.getKey());
          output.print(SEPARATOR);
          output.println(extractTextData(issue));
        });
        output.flush();
      }
    } catch (IOException e) {

      // LOGGER.error("Could not write issue: " + issue.getKey() + " in dataSetFile: " + dataSetFile.getName(), e);
    }
  }

  private String extractTextData(Issue issue) {
    String text = "";
    if (issue.getDescription() != null) {
      text = issue.getSummary() + " " + issue.getDescription().trim().replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " ");
    }
    return text;
  }
}
