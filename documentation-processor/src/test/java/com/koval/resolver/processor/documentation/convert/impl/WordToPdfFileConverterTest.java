package com.koval.resolver.processor.documentation.convert.impl;

import java.io.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.core.FileRepository;

@ExtendWith(MockitoExtension.class)
class WordToPdfFileConverterTest {

  private final static String DOC_TEXT = "doc text";

  private final static String FILE_NAME = "file";
  private final static String DOC_FILE_PATH = FILE_NAME + ".doc";
  private final static String PDF_FILE_PATH = FILE_NAME + ".pdf";

  @Mock
  private FileRepository fileRepository;

  @Mock
  private XwpfPdfConverter pdfConverter;

  private WordToPdfFileConverter wordToPdfFileConverter;

  @BeforeEach
  void setUp() {
    wordToPdfFileConverter = new WordToPdfFileConverter(
            fileRepository,
            pdfConverter
    );

  }

  @Test
  void testConvertingDocToPdf() throws IOException {
    InputStream docInputStream = new ByteArrayInputStream(DOC_TEXT.getBytes());
    when(fileRepository.readFile(DOC_FILE_PATH)).thenReturn(docInputStream);

    OutputStream outputPdfStream = new ByteArrayOutputStream();
    when(fileRepository.writeToFile(PDF_FILE_PATH)).thenReturn(outputPdfStream);

    wordToPdfFileConverter.convert(DOC_FILE_PATH, PDF_FILE_PATH);

    verify(pdfConverter).convert(docInputStream, outputPdfStream);
  }

}
