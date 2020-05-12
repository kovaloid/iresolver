package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetadataFileEntryWriterTest {

  private static final String DOC_PAGE_KEY = "key";
  private static final int CURRENT_DOCUMENT_INDEX = 1;
  private static final int DOC_PAGE_NUMBER = 2;
  private static final String DELIMITER = " ";
  private static final String LINE_BREAK = System.lineSeparator();

  private static final String EXPECTED_RESULT = createExpectedResult();

  private static String createExpectedResult() {
    return DOC_PAGE_KEY
            + DELIMITER
            + CURRENT_DOCUMENT_INDEX
            + DELIMITER
            + DOC_PAGE_NUMBER
            + LINE_BREAK;
  }

  private MetadataFileEntryWriter metadataFileEntryWriter;

  @BeforeEach
  void onSetup() {
    metadataFileEntryWriter = new MetadataFileEntryWriter();
  }

  @Test
  void testWriteMetadata() throws IOException {
    final StringWriter stringWriter = new StringWriter();
    final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);

    metadataFileEntryWriter.write(
            bufferedWriter,
            DOC_PAGE_KEY,
            CURRENT_DOCUMENT_INDEX,
            DOC_PAGE_NUMBER,
            DELIMITER

    );

    bufferedWriter.flush();

    final String actualResult = stringWriter.toString();

    assertEquals(EXPECTED_RESULT, actualResult);
  }
}
