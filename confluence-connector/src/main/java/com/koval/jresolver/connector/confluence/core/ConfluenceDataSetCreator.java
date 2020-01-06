package com.koval.jresolver.connector.confluence.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.koval.jresolver.common.api.util.TextUtil;


public class ConfluenceDataSetCreator implements Closeable {

  private final PrintWriter dataFileOutput;
  private final PrintWriter metadataFileOutput;

  public ConfluenceDataSetCreator() throws FileNotFoundException, UnsupportedEncodingException {
    dataFileOutput = new PrintWriter(new File("../data/", "ConfluenceDataSet.txt"), StandardCharsets.UTF_8.name());
    metadataFileOutput = new PrintWriter(new File("../data/", "MetadataConfluenceDataSet.txt"), StandardCharsets.UTF_8.name());
  }

  public void handle(List<Content> results) {
    results.forEach((Content result) -> {
      dataFileOutput.print(result.getId().serialise());
      dataFileOutput.print("|");
      String bodyWithoutTags = result.getBody()
          .get(ContentRepresentation.STORAGE)
          .getValue()
          .replaceAll("nbsp", "")
          .replaceAll("<.*?>", "");
      dataFileOutput.println(TextUtil.simplify(bodyWithoutTags));

      metadataFileOutput.print(result.getId().serialise());
      metadataFileOutput.print(" ");
      metadataFileOutput.println(result.getTitle());
    });
    dataFileOutput.flush();
    metadataFileOutput.flush();
  }

  @Override
  public void close() throws IOException {
    dataFileOutput.close();
    metadataFileOutput.close();
  }
}
