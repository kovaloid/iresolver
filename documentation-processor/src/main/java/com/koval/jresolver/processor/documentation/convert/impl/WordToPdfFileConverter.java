package com.koval.jresolver.processor.documentation.convert.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.processor.documentation.convert.FileConverter;


public class WordToPdfFileConverter implements FileConverter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordToPdfFileConverter.class);

  @Override
  public void convert(File inputFile) {
    String wordFileName = inputFile.getName();
    String pdfFileName = wordFileName.substring(0, wordFileName.lastIndexOf('.')).concat(".pdf");

    try (InputStream doc = new FileInputStream(inputFile);
         OutputStream out = new FileOutputStream(new File("../docs", pdfFileName))) {
      XWPFDocument document = new XWPFDocument(doc);
      PdfOptions options = PdfOptions.create();
      PdfConverter.getInstance().convert(document, out, options);
      LOGGER.info("PDF file created: " + pdfFileName);
    } catch (IOException e) {
      LOGGER.error("Could not convert word file " + wordFileName + " to pdf " + pdfFileName, e);
    }
  }
}
