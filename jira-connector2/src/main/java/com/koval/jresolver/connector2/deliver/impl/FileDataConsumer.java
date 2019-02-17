package com.koval.jresolver.connector2.deliver.impl;

import com.koval.jresolver.connector2.bean.JiraIssue;
import com.koval.jresolver.connector2.deliver.DataConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class FileDataConsumer implements DataConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileDataConsumer.class);

  private final File dataSetFile;

  public FileDataConsumer(File dataSetFile) {
    this.dataSetFile = dataSetFile;
    LOGGER.info("New data set dataSetFile will be created: {}", this.dataSetFile.getAbsolutePath());
  }

  @Override
  public void consume(JiraIssue issue) {
    String key = "";
    String text = "";

    if (issue.getKey() != null && !issue.getKey().isEmpty()) {
      key = issue.getKey();
    }
    if (issue.getDescription() != null) {
      text = issue.getSummary() + " " + issue.getDescription().trim().replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " ");
    }

    if (!key.isEmpty() && !text.isEmpty()) {
      /*try (FileWriter fileWriter = new FileWriter(dataSetFile, isAppend);
           BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
           PrintWriter out = new PrintWriter(bufferedWriter)) {*/



      try (OutputStream outputStream = new FileOutputStream(dataSetFile, true);
           Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
           BufferedWriter bufferedWriter = new BufferedWriter(writer);
           PrintWriter out = new PrintWriter(bufferedWriter)) {


        out.print(key);
        out.print(" | ");
        out.println(text);
      } catch (IOException e) {
        LOGGER.error("Could not write issue: " + issue.getKey() + " in dataSetFile: " + dataSetFile.getName(), e);
      }
    }
  }
}
