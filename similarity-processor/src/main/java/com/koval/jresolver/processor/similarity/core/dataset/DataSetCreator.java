package com.koval.jresolver.processor.similarity.core.dataset;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;

public class DataSetCreator {

  private static final String SEPARATOR = " | ";
  private static final String DATASET_FILE_NAME = "DataSet.txt";

  private final IssuesReceiver receiver;

  public DataSetCreator(IssuesReceiver receiver) {
    this.receiver = receiver;
  }

  public void create() {
    File dataSetFile = new File(DATASET_FILE_NAME);
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
