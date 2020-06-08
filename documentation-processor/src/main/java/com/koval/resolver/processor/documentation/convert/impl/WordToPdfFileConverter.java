package com.koval.resolver.processor.documentation.convert.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;
import com.koval.resolver.processor.documentation.core.FileRepository;


public class WordToPdfFileConverter implements FileConverter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordToPdfFileConverter.class);

  private final FileRepository fileRepository;
  private final XwpfPdfConverter pdfConverter;

  public WordToPdfFileConverter(
          FileRepository fileRepository,
          XwpfPdfConverter pdfConverter
  ) {
    this.fileRepository = fileRepository;
    this.pdfConverter = pdfConverter;
  }

  @Override
  public void convert(
          final String inputFilePath,
          final String outputFilePath
  ) {
    try (InputStream doc = fileRepository.readFile(inputFilePath);
         OutputStream out = fileRepository.writeToFile(outputFilePath)) {
      pdfConverter.convert(doc, out);

      LOGGER.info("PDF file created: " + outputFilePath);
    } catch (IOException e) {
      LOGGER.error("Could not convert word file " + inputFilePath + " to pdf " + outputFilePath, e);
    }
  }

  @Override
  public MediaType getConvertibleType(MediaType docType) {
    return MediaType.WORD;
  }
}
