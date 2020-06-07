package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataSetFileEntryWriterTest {
  private static final String PAGE_KEY = "key";
  private static final String PAGE_TEXT = "text";
  private static final String DELIMITER = " ";
  private static final String LINE_BREAK = System.lineSeparator();

  private static final String EXPECTED_RESULT = createExpectedResult();

  private static String createExpectedResult() {
    return PAGE_KEY
            + DELIMITER
            + PAGE_TEXT
            + LINE_BREAK;
  }

  private DataSetFileEntryWriter dataSetFileEntryWriter;

  @BeforeEach
  void onSetup() {
    dataSetFileEntryWriter = new DataSetFileEntryWriter();
  }

  @Test
  void testWriteDataSetFileEntry() throws IOException {
    final StringWriter stringWriter = new StringWriter();
    final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);

    dataSetFileEntryWriter.write(
            bufferedWriter,
            PAGE_KEY,
            PAGE_TEXT,
            DELIMITER
    );

    bufferedWriter.flush();

    final String actualResult = stringWriter.toString();

    assertEquals(EXPECTED_RESULT, actualResult);
  }
}
