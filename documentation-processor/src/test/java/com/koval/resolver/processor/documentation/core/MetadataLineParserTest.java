package com.koval.resolver.processor.documentation.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

//TODO: make tests of handling invalid strings pass
public class MetadataLineParserTest {

  private static final String DELIMITER = " ";

  private static final DocMetadata EXPECTED_METADATA = new DocMetadata("key1", 14, 23);
  private static final String METADATA_STRING = constructTestString(EXPECTED_METADATA);

  private static final String INVALID_STRING_ONLY_KEY = "5";
  private static final String INVALID_STRING_FILE_INDEX_NOT_NUMBER = "a b";
  private static final String INVALID_STRING_WITHOUT_PAGE_NUMBER = "a 5";
  private static final String INVALID_STRING_PAGE_NUMBER_NOT_NUMBER = "a 5 c";

  private MetadataLineParser metadataLineParser;

  @BeforeEach
  void onSetup() {
    metadataLineParser = new MetadataLineParser(DELIMITER);
  }

  @Test
  void testParseValidString() {
    final DocMetadata actualMetaData = metadataLineParser.parseLine(METADATA_STRING);

    assertMetadataEqual(EXPECTED_METADATA, actualMetaData);
  }

  @Disabled("Handle error when string contains only key")
  @Test
  void testParsingInvalidStringOnlyKey() {
    metadataLineParser.parseLine(INVALID_STRING_ONLY_KEY);
  }

  @Disabled("Handle error when string does not have page number")
  @Test
  void testParsingInvalidStringWithoutPageNumber() {
    metadataLineParser.parseLine(INVALID_STRING_WITHOUT_PAGE_NUMBER);
  }

  @Disabled("Handle error when file index is not number")
  @Test
  void testParsingInvalidStringFileIndexNotNumber() {
    metadataLineParser.parseLine(INVALID_STRING_FILE_INDEX_NOT_NUMBER);
  }

  @Disabled("Handle error when page number is not number")
  @Test
  void testParsingInvalidStringPageNumberNotNumber() {
    metadataLineParser.parseLine(INVALID_STRING_PAGE_NUMBER_NOT_NUMBER);
  }

  private static String constructTestString(final DocMetadata docMetadata) {
    return docMetadata.getKey()
            + DELIMITER + docMetadata.getFileIndex()
            + DELIMITER + docMetadata.getPageNumber();
  }

  private void assertMetadataEqual(final DocMetadata expectedMetaData, final DocMetadata actualMetaData) {
    assertEquals(expectedMetaData.getKey(), actualMetaData.getKey());
    assertEquals(expectedMetaData.getFileIndex(), actualMetaData.getFileIndex());
    assertEquals(expectedMetaData.getPageNumber(), actualMetaData.getPageNumber());
  }
}
