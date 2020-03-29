package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocOutputFilesParserTest {
  private static final DocMetadata DOC_METADATA = new DocMetadata("lol", 14, 23);
  private static final String METADATA_STRING = "lol 14 23";
  private static final DocumentationProcessorConfiguration PROPERTIES = new DocumentationProcessorConfiguration();

  @TempDir
  File directory;

  private DocOutputFilesParser mDocOutputFilesParser;

  @BeforeEach
  private void onSetup() throws IOException {
    final File tempFile = new File(directory, "tempFile.txt");
    FileUtils.writeStringToFile(tempFile, METADATA_STRING);

    PROPERTIES.setDocsMetadataFile(tempFile.getPath());

    mDocOutputFilesParser = new DocOutputFilesParser(PROPERTIES);
  }

  @Test
  void testParsingDocMeta() {
    List<DocMetadata> metadata = mDocOutputFilesParser.parseDocumentationMetadata();

    assertEquals(DOC_METADATA.getFileIndex(), metadata.get(0).getFileIndex());
  }

}
