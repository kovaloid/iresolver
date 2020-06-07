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
  private FileRepository fileRepository;

  @Mock
  private LineParser<String> stringLineParser;

  private FileParser fileParser;

  @BeforeEach
  void onSetup() throws FileNotFoundException {
    final InputStream inputStream = new ByteArrayInputStream(FILE_STRINGS.getBytes());
    when(fileRepository.readFile(FILE_NAME)).thenReturn(inputStream);

    when(stringLineParser.parseLine(FILE_STRING_1)).thenReturn(PARSED_STRING_1);
    when(stringLineParser.parseLine(FILE_STRING_2)).thenReturn(PARSED_STRING_2);

    fileParser = new FileParser(fileRepository);
  }

  @Test
  void testReadingLines() {
    fileParser.parseFile(FILE_NAME, stringLineParser);

    verify(stringLineParser).parseLine(FILE_STRING_1);
    verify(stringLineParser).parseLine(FILE_STRING_2);
  }


  @Test
  void testReturningParsedLines() {
    final List<String> parsedStrings = fileParser.parseFile(FILE_NAME, stringLineParser);

    assertEquals(PARSED_STRING_1, parsedStrings.get(0));
    assertEquals(PARSED_STRING_2, parsedStrings.get(1));
  }
}
