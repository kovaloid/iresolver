package com.koval.jresolver.connector.confluence.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentRepresentation;


public class ConfluenceDataSetCreator implements Closeable {

  private final PrintWriter dataFileOutput;

  public ConfluenceDataSetCreator() throws FileNotFoundException, UnsupportedEncodingException {
    dataFileOutput = new PrintWriter(new File("../data/", "ConfluenceDataSet.txt"), StandardCharsets.UTF_8.name());
  }

  public void handle(List<Content> results) {
    results.forEach((Content result) -> {
      dataFileOutput.println("Page ID: " + result.getId().serialise());
      dataFileOutput.println("Page Title: " + result.getTitle());
      dataFileOutput.println("Page Body: " + result.getBody().get(ContentRepresentation.STORAGE).getValue()
          .replace('\n', ' ')
          .replaceAll("\\<.*?\\>", ""));
    });
    dataFileOutput.flush();
  }

  @Override
  public void close() throws IOException {
    dataFileOutput.close();
  }
}
