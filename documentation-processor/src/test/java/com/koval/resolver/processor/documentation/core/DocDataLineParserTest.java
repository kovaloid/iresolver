package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: make tests of handling invalid strings pass
public class DocDataLineParserTest {
  private static final String DELIMITER = " ";

  private static final DocFile EXPECTED_DOC_FILE = new DocFile(15, "filename1");
  private static final String DOC_FILE_STRING = constructTestString(EXPECTED_DOC_FILE);

  private static final String INVALID_STRING_ONLY_FILE_INDEX = "5";
  private static final String INVALID_STRING_FILE_INDEX_NOT_NUMBER = "a b";

  private DocDataLineParser mDocDataLineParser;

  @BeforeEach
  void onSetup() {
    mDocDataLineParser = new DocDataLineParser(DELIMITER);
  }

  @Test
  void testParseValidString() {
    DocFile parsedDocFile = mDocDataLineParser.parseLine(DOC_FILE_STRING);

    assertDocFilesEqual(EXPECTED_DOC_FILE, parsedDocFile);
  }

  @Test
  void testParseInvalidStringOnlyFileIndex() {
    mDocDataLineParser.parseLine(INVALID_STRING_ONLY_FILE_INDEX);
  }

  @Test
  void testParseInvalidStringFileIndexNotNumber() {
    mDocDataLineParser.parseLine(INVALID_STRING_FILE_INDEX_NOT_NUMBER);
  }

  private static String constructTestString(DocFile docFile) {
    return docFile.getFileIndex() + DELIMITER + docFile.getFileName();
  }

  private void assertDocFilesEqual(DocFile expectedDocFile, DocFile actualDocFile) {
    assertEquals(expectedDocFile.getFileIndex(), actualDocFile.getFileIndex());
    assertEquals(expectedDocFile.getFileName(), actualDocFile.getFileName());
  }

}

