package com.koval.resolver.processor.documentation.convert.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;


public class HtmlToPdfFileConverter implements FileConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(WordToPdfFileConverter.class);

    @Override
  public void convert(final String inputFilePath, final String outputFilePath) {
    Pdf pdf = new Pdf();
    try {
      pdf.addPageFromUrl(inputFilePath);
      pdf.saveAs(outputFilePath);
      LOGGER.info("PDF file created: " + inputFilePath);

    } catch (IOException | InterruptedException e) {
      LOGGER.error("Could not convert word file " + inputFilePath + " to pdf " + outputFilePath, e);
    }
  }

  @Override
  public MediaType getConvertibleType(MediaType docType) {
      return MediaType.HTML;
  }
}
