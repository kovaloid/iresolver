package com.koval.resolver.processor.confluence.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.confluence.ConfluencePage;
import com.koval.resolver.common.api.component.processor.DataSetWriter;
import com.koval.resolver.common.api.configuration.bean.processors.ConfluenceProcessorConfiguration;
import com.koval.resolver.common.api.util.TextUtil;


public class ConfluenceDataSetWriter implements DataSetWriter<ConfluencePage> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceDataSetWriter.class);

  private final PrintWriter dataFileOutput;
  private final PrintWriter metadataFileOutput;

  public ConfluenceDataSetWriter(ConfluenceProcessorConfiguration properties) throws FileNotFoundException, UnsupportedEncodingException {
    dataFileOutput = new PrintWriter(new File(properties.getDataSetFile()), StandardCharsets.UTF_8.name());
    metadataFileOutput = new PrintWriter(new File(properties.getConfluenceMetadataFile()), StandardCharsets.UTF_8.name());
    LOGGER.info("Confluence data set writer created");
  }

  @Override
  public void write(List<ConfluencePage> results) {
    results.forEach((ConfluencePage result) -> {
      LOGGER.info("Page with id {} and name {} was written to the confluence data set",
          result.getId(), result.getTitle());

      dataFileOutput.print(result.getId());
      dataFileOutput.print("|");
      dataFileOutput.println(TextUtil.simplify(result.getBody()));

      metadataFileOutput.print(result.getId());
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
