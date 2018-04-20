package com.koval.jresolver.connector.deliver.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.deliver.DataConsumer;


public class FileDataConsumer implements DataConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileDataConsumer.class);

  private File file;
  private boolean isAppend;

  public FileDataConsumer(String dataSetFileName) {
    this(dataSetFileName, true);
  }

  public FileDataConsumer(String dataSetFileName, boolean isAppend) {
    this.file = new File(dataSetFileName);
    this.isAppend = isAppend;
    LOGGER.info("New data set file will be created: {}", file.getAbsolutePath());
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
      /*try (FileWriter fileWriter = new FileWriter(file, isAppend);
           BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
           PrintWriter out = new PrintWriter(bufferedWriter)) {*/



      try (OutputStream outputStream = new FileOutputStream(file, isAppend);
           Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
           BufferedWriter bufferedWriter = new BufferedWriter(writer);
           PrintWriter out = new PrintWriter(bufferedWriter)) {


        out.print(key);
        out.print(" | ");
        out.println(text);
      } catch (IOException e) {
        LOGGER.error("Could not write issue: " + issue.getKey() + " in file: " + file.getName(), e);
      }
    }
  }
}
