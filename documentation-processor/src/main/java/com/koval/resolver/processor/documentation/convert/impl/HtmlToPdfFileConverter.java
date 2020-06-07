package com.koval.resolver.processor.documentation.convert.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.koval.resolver.processor.documentation.convert.FileConverter;


public class HtmlToPdfFileConverter implements FileConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(WordToPdfFileConverter.class);

  @Override
  public void convert(File inputFile) {
    String htmlFileName = inputFile.getName();
    String pdfFileName = htmlFileName.substring(0, htmlFileName.lastIndexOf('.')).concat(".pdf");
    Pdf pdf = new Pdf();
    try {
      pdf.addPageFromUrl(inputFile.getPath());
      pdf.saveAs("../docs/" + pdfFileName);
      LOGGER.info("PDF file created: " + pdfFileName);

    } catch (IOException | InterruptedException e) {
      LOGGER.error("Could not convert word file " + htmlFileName + " to pdf " + pdfFileName, e);
    }
  }

  public void convert(String webPageUrl) {
    String pdfFileName = webPageUrl.concat(".pdf");
    Pdf pdf = new Pdf();
    try {
      pdf.addPageFromUrl(webPageUrl);
      pdf.saveAs("../docs/" + pdfFileName);
      LOGGER.info("PDF file created: " + pdfFileName);
    } catch (IOException | InterruptedException e) {
      LOGGER.error("Could not convert word file " + webPageUrl + " to pdf " + pdfFileName, e);
    }

  }
}
