package com.koval.jresolver.connector.confluence.core;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.koval.jresolver.common.api.configuration.bean.processors.ConfluenceProcessorConfiguration;
import com.koval.jresolver.common.api.util.TextUtil;


public class ConfluenceDataSetWriter implements Closeable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceDataSetWriter.class);

  private final PrintWriter dataFileOutput;
  private final PrintWriter metadataFileOutput;

  public ConfluenceDataSetWriter(ConfluenceProcessorConfiguration properties) throws FileNotFoundException, UnsupportedEncodingException {
    dataFileOutput = new PrintWriter(new File(properties.getDataSetFile()), StandardCharsets.UTF_8.name());
    metadataFileOutput = new PrintWriter(new File(properties.getConfluenceMetadataFile()), StandardCharsets.UTF_8.name());
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
