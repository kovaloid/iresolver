package com.koval.jresolver.connector.confluence.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.koval.jresolver.common.api.util.TextUtil;
import com.koval.jresolver.connector.confluence.configuration.ConfluenceConnectorProperties;


public class ConfluenceDataSetWriter implements Closeable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceDataSetWriter.class);

  private final PrintWriter dataFileOutput;
  private final PrintWriter metadataFileOutput;

  public ConfluenceDataSetWriter(ConfluenceConnectorProperties properties) throws FileNotFoundException, UnsupportedEncodingException {
    dataFileOutput = new PrintWriter(new File(properties.getWorkFolder(), "Confluence_DataSet.txt"), StandardCharsets.UTF_8.name());
    metadataFileOutput = new PrintWriter(new File(properties.getWorkFolder(), "MetadataConfluence_DataSet.txt"), StandardCharsets.UTF_8.name());
    LOGGER.info("Confluence data set writer created");
  }

  public void write(List<Content> results) {
    results.forEach((Content result) -> {
      LOGGER.info("Page with id {} and name {} was written to the confluence data set",
          result.getId().serialise(), result.getTitle());

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
    LOGGER.info("Confluence data set writer has been closed");
  }
}
