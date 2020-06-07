package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocListFileEntryWriterTest {

  private static final int FILE_INDEX = 1;
  private static final String FILE_NAME = "filename";
  private static final String DELIMITER = " ";
  private static final String LINE_BREAK = System.lineSeparator();

  private static final String EXPECTED_RESULT = createExpectedResult();

  private static String createExpectedResult() {
    return FILE_INDEX
            + DELIMITER
            + FILE_NAME
            + LINE_BREAK;
  }

  private DocListFileEntryWriter docListFileEntryWriter;

  @BeforeEach
  void onSetup() {
    docListFileEntryWriter = new DocListFileEntryWriter();
  }

  @Test
  void testWriteDocListEntry() throws IOException {
    final StringWriter stringWriter = new StringWriter();
    final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);

    docListFileEntryWriter.write(
            bufferedWriter,
            FILE_INDEX,
            FILE_NAME,
            DELIMITER
    );

    bufferedWriter.flush();

    final String actualResult = stringWriter.toString();

    assertEquals(EXPECTED_RESULT, actualResult);
  }
}
