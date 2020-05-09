package com.koval.resolver.processor.documentation.convert.impl;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.processor.documentation.core.FileRepository;

@ExtendWith(MockitoExtension.class)
class WordToPdfFileConverterTest {

  @Mock
  private PdfConverter pdfConverter;

  @Mock
  private FileRepository fileRepository;

  private WordToPdfFileConverter wordToPdfFileConverter;

  @BeforeEach
  void setUp() {
    wordToPdfFileConverter = new WordToPdfFileConverter(fileRepository);
  }

  @Test
  void convert() {
//    wordToPdfFileConverter.convert();
  }
}
