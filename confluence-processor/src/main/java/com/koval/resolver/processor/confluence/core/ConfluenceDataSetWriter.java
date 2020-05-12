package com.koval.resolver.processor.confluence.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

  public ConfluenceDataSetWriter(final ConfluenceProcessorConfiguration properties) throws IOException {
    final File dataSetFile = new File(properties.getDataSetFile());
    final File metadataFile = new File(properties.getConfluenceMetadataFile());
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getCanonicalPath());
    FileUtils.forceMkdir(metadataFile.getParentFile());
    LOGGER.info("Folder to store metadata file created: {}", metadataFile.getParentFile().getCanonicalPath());

    dataFileOutput = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name());
    metadataFileOutput = new PrintWriter(metadataFile, StandardCharsets.UTF_8.name());
    LOGGER.info("Confluence data set writer created");
  }

  @Override
  public void write(final List<ConfluencePage> results) {
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
