package com.koval.jresolver.connector.jira.core.processing.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.processing.IssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.IssuesReceiver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class FileIssuesGenerator implements IssuesGenerator<File> {

  private static final String SEPARATOR = " | ";
  private static final String DATASET_FILE_NAME = "DataSet.txt";

  private IssuesReceiver receiver;

  public FileIssuesGenerator(IssuesReceiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public void launch() {
    FileIssuesHandler handler = new FileIssuesHandler();
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
      while (receiver.hasNext()) {
        Collection<Issue> issues = receiver.getNextIssues();
        issues.forEach(issue -> {
          output.print(issue.getKey());
          output.print(SEPARATOR);
          output.println(handler.extractTextData(issue));
        });
        output.flush();
      }
    } catch (IOException e) {

      // LOGGER.error("Could not write issue: " + issue.getKey() + " in dataSetFile: " + dataSetFile.getName(), e);
    }
  }

  @Override
  public File getResults() {
    return new File(DATASET_FILE_NAME);
  }
}
