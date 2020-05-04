package com.koval.resolver.processor.documentation.core;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;

import static util.ConfigurationPropertiesCreator.createProperties;

public class DocDataSetCreatorTest {

  private static final String DOC_FILE_STRING_1 = "15 filename1\n";
  private static final String DOC_FILE_STRING_2 = "26 filename2";

  private static final List<String> DOC_FILE_STRINGS = Arrays.asList(DOC_FILE_STRING_1, DOC_FILE_STRING_2);


  private DocDataSetCreator docDataSetCreator;

  @TempDir
  File tempDirectory;

  private File tempFile;

  @BeforeEach
  void onSetup() throws IOException {
    DocumentationProcessorConfiguration properties = createConfigurationProperties();
    docDataSetCreator = new DocDataSetCreator(properties);
  }

  @Test
  void testConvertWordIntoPdf() {
    docDataSetCreator.convertWordFilesToPdf();
  }

  private DocumentationProcessorConfiguration createConfigurationProperties() throws IOException {
    if (tempFile != null) {
      tempFile.delete();
    }

    tempFile = new File(tempDirectory, "tempFile.txt");

    return createProperties(DOC_FILE_STRINGS, tempFile);
  }

}
