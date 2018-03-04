package com.koval.jresolver.extraction.deliver.impl;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.extraction.deliver.DataConsumer;
import com.koval.jresolver.extraction.issue.IssueHandler;

import java.io.*;


public class BasicDataConsumer implements DataConsumer {

  private IssueHandler issueHandler;
  private String fileName = "raw2.txt";

  public BasicDataConsumer() {
    File file = new File(fileName);
    if (file.exists() && file.isFile()) {
      file.delete();
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void consume(Issue issue) {

    String user = "null";
    String label = "null";
    String text = "null";

    if (issue.getReporter() != null) {
      user = issue.getReporter().getName();
    }
    if (issue.getLabels() != null && !issue.getLabels().isEmpty()) {
      label = issue.getLabels().iterator().next();
    }
    if (issue.getDescription() != null) {
      text = issue.getDescription().trim().replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " ");
    }


    //if (!user.isEmpty() && !label.isEmpty() && !text.isEmpty()) {

      try (FileWriter fileWriter = new FileWriter(fileName, true);
           BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
           PrintWriter out = new PrintWriter(bufferedWriter))
      {
        out.print(label);
        out.print(",");
        out.print(user);
        out.print(" | ");
        out.println(text);
      } catch (IOException e) {
        e.printStackTrace();
      }

    //}


    System.out.println(issue.getSummary());
  }
}
