package com.koval.resolver.processor.documentation.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.koval.resolver.processor.documentation.bean.DocFile;

//TODO: make tests of handling invalid strings pass
public class DocDataLineParserTest {

  private static final String DELIMITER = " ";

  private static final DocFile EXPECTED_DOC_FILE = new DocFile(15, "filename1");
  private static final String DOC_FILE_STRING = constructTestString(EXPECTED_DOC_FILE);

  private static final String INVALID_STRING_ONLY_FILE_INDEX = "5";
  private static final String INVALID_STRING_FILE_INDEX_NOT_NUMBER = "a b";

  private DocDataLineParser docDataLineParser;

  @BeforeEach
  void onSetup() {
    docDataLineParser = new DocDataLineParser(DELIMITER);
  }

  @Test
  void testParseValidString() {
    final DocFile parsedDocFile = docDataLineParser.parseLine(DOC_FILE_STRING);

    assertDocFilesEqual(EXPECTED_DOC_FILE, parsedDocFile);
  }

  @Disabled("Handle error when string is without file name")
  @Test
  void testParseInvalidStringOnlyFileIndex() {
    docDataLineParser.parseLine(INVALID_STRING_ONLY_FILE_INDEX);
  }

  @Disabled("Handle error when string is without file index")
  @Test
  void testParseInvalidStringFileIndexNotNumber() {
    docDataLineParser.parseLine(INVALID_STRING_FILE_INDEX_NOT_NUMBER);
  }

  private static String constructTestString(final DocFile docFile) {
    return docFile.getFileIndex() + DELIMITER + docFile.getFileName();
  }

  private void assertDocFilesEqual(final DocFile expectedDocFile, final DocFile actualDocFile) {
    assertEquals(expectedDocFile.getFileIndex(), actualDocFile.getFileIndex());
    assertEquals(expectedDocFile.getFileName(), actualDocFile.getFileName());
  }
}
