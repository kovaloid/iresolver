package com.koval.jresolver.connector.deliver.impl;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.connector.deliver.DataConsumer;

import java.io.*;


public class FileDataConsumer implements DataConsumer {

  private File file;

  public FileDataConsumer(File file) {
    this.file = file;
  }

  @Override
  public void consume(Issue issue) {
    String key = "null";
    String text = "null";

    if (issue.getKey() != null && !issue.getKey().isEmpty()) {
      key = issue.getKey();
    }
    if (issue.getDescription() != null) {
      text = issue.getSummary() + " " + issue.getDescription().trim().replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " ");
    }

    //if (!user.isEmpty() && !label.isEmpty() && !text.isEmpty()) {
      try (FileWriter fileWriter = new FileWriter(file, true);
           BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
           PrintWriter out = new PrintWriter(bufferedWriter))
      {
        out.print(key);
        out.print(" | ");
        out.println(text);
      } catch (IOException e) {
        e.printStackTrace();
      }
    //}
  }
}
