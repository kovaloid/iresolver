package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: make tests of handling invalid strings pass
public class MetadataLineParserTest {
  private static final String DELIMITER = " ";

  private static final DocMetadata EXPECTED_METADATA = new DocMetadata("key1", 14, 23);
  private static final String METADATA_STRING = constructTestString(EXPECTED_METADATA);

  private static final String INVALID_STRING_ONLY_KEY = "5";
  private static final String INVALID_STRING_ONLY_FILE_INDEX_NOT_NUMBER = "a b";
  private static final String INVALID_STRING_ONLY_PAGE_NUMBER_NOT_NUMBER = "a 5 c";

  private MetadataLineParser mMetadataLineParser;

  @BeforeEach
  void onSetup() {
    mMetadataLineParser = new MetadataLineParser(DELIMITER);
  }

  @Test
  void testParseValidString() {
    DocMetadata actualMetaData = mMetadataLineParser.parseLine(METADATA_STRING);

    assertMetadataEqual(EXPECTED_METADATA, actualMetaData);
  }

  @Test
  void testParsingInvalidStringOnlyKey() {
    mMetadataLineParser.parseLine(INVALID_STRING_ONLY_KEY);
  }

  @Test
  void testParsingInvalidStringFileIndexNotNumber() {
    mMetadataLineParser.parseLine(INVALID_STRING_ONLY_FILE_INDEX_NOT_NUMBER);
  }

  @Test
  void testParsingInvalidStringPageNumberNotNumber() {
    mMetadataLineParser.parseLine(INVALID_STRING_ONLY_PAGE_NUMBER_NOT_NUMBER);
  }

  private static String constructTestString(DocMetadata docMetadata) {
    return docMetadata.getKey()
            + DELIMITER + docMetadata.getFileIndex()
            + DELIMITER + docMetadata.getPageNumber();
  }

  private void assertMetadataEqual(DocMetadata expectedMetaData, DocMetadata actualMetaData) {
    assertEquals(expectedMetaData.getKey(), actualMetaData.getKey());
    assertEquals(expectedMetaData.getFileIndex(), actualMetaData.getFileIndex());
    assertEquals(expectedMetaData.getPageNumber(), actualMetaData.getPageNumber());
  }

}
