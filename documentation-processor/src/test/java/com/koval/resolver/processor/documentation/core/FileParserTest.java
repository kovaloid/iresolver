package com.koval.resolver.processor.documentation.core;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileParserTest {

  private static final String FILE_NAME = "FILE_NAME";

  private static final String FILE_STRING_1 = "a b c";
  private static final String FILE_STRING_2 = "d e f";
  private static final String FILE_STRINGS = FILE_STRING_1 + "\n" + FILE_STRING_2;

  private static final String PARSED_STRING_1 = "x";
  private static final String PARSED_STRING_2 = "z";

  @Mock
  DocFileRepository mDocFileRepository;

  @Mock
  LineParser<String> mStringLineParser;

  private FileParser mFileParser;

  @BeforeEach
  void onSetup() throws FileNotFoundException {
    MockitoAnnotations.initMocks(this);

    InputStream inputStream = new ByteArrayInputStream(FILE_STRINGS.getBytes());
    when(mDocFileRepository.getFile(FILE_NAME)).thenReturn(inputStream);

    when(mStringLineParser.parseLine(FILE_STRING_1)).thenReturn(PARSED_STRING_1);
    when(mStringLineParser.parseLine(FILE_STRING_2)).thenReturn(PARSED_STRING_2);

    mFileParser = new FileParser(mDocFileRepository);
  }

  @Test
  void testReadingLines() {
    mFileParser.parseFile(FILE_NAME, mStringLineParser);

    verify(mStringLineParser).parseLine(FILE_STRING_1);
    verify(mStringLineParser).parseLine(FILE_STRING_2);
  }


  @Test
  void testReturningParsedLines() {
    List<String> parsedStrings = mFileParser.parseFile(FILE_NAME, mStringLineParser);

    assertEquals(PARSED_STRING_1, parsedStrings.get(0));
    assertEquals(PARSED_STRING_2, parsedStrings.get(1));
  }

}
