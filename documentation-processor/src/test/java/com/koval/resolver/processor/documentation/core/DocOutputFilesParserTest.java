package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocOutputFilesParserTest {
  private static final DocMetadata DOC_METADATA_1 = new DocMetadata("key1", 14, 23);
  private static final String METADATA_STRING_1 = "key1 14 23\n";

  private static final DocMetadata DOC_METADATA_2 = new DocMetadata("key2", 25, 67);
  private static final String METADATA_STRING_2 = "key2 25 67";

  private static final List<String> METADATA_STRINGS = Arrays.asList(METADATA_STRING_1, METADATA_STRING_2);

  @TempDir
  File tempDirectory;

  private File tempFile;

  private DocOutputFilesParser mDocOutputFilesParser;

  @BeforeEach
  void onSetup() throws IOException {
    tempFile = new File(tempDirectory, "tempFile.txt");

    DocumentationProcessorConfiguration properties = createProperties(METADATA_STRINGS);

    mDocOutputFilesParser = new DocOutputFilesParser(properties);
  }

  @Test
  void testParsingOneLineDocMeta() {
    DocMetadata actualMetaData = mDocOutputFilesParser.parseDocumentationMetadata().get(0);

    assertMetadataEqual(DOC_METADATA_1, actualMetaData);
  }

  @Test
  void testParsingMultipleDocMeta() {
    List<DocMetadata> metadataList = mDocOutputFilesParser.parseDocumentationMetadata();

    assertMetadataEqual(DOC_METADATA_1, metadataList.get(0));
    assertMetadataEqual(DOC_METADATA_2, metadataList.get(1));
  }

  private DocumentationProcessorConfiguration createProperties(List<String> metadataStrings) throws IOException {
    final File tempFile = writeStringsToFile(metadataStrings);

    DocumentationProcessorConfiguration properties = new DocumentationProcessorConfiguration();
    properties.setDocsMetadataFile(tempFile.getPath());

    return properties;
  }

  private void assertMetadataEqual(DocMetadata expectedMetaData, DocMetadata actualMetaData) {
    assertEquals(expectedMetaData.getKey(), actualMetaData.getKey());
    assertEquals(expectedMetaData.getFileIndex(), actualMetaData.getFileIndex());
    assertEquals(expectedMetaData.getPageNumber(), actualMetaData.getPageNumber());
  }

  private File writeStringsToFile(List<String> metadataStrings) throws IOException {
    for (String metadataString : metadataStrings) {
      FileUtils.writeStringToFile(tempFile, metadataString, Charset.defaultCharset(), true);
    }

    return tempFile;
  }


}
