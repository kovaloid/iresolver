package com.koval.resolver.processor.documentation.convert.impl;

import java.io.*;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.processor.documentation.convert.FileConverter;
import com.koval.resolver.processor.documentation.core.FileRepository;


public class WordToPdfFileConverter implements FileConverter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordToPdfFileConverter.class);

  private static final String EXTENSION_PDF = ".pdf";
  private static final String DOC_FILES_LOCATION = "../docs";

  private FileRepository fileRepository;

  public WordToPdfFileConverter(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Override
  public void convert(File inputFile) {
    String wordFileName = inputFile.getName();
    String pdfFileName = createPdfFileName(wordFileName);

    try (InputStream doc = fileRepository.readFile(wordFileName);
         OutputStream out = new FileOutputStream(new File(DOC_FILES_LOCATION, pdfFileName))) {
      XWPFDocument document = new XWPFDocument(doc);
      PdfOptions options = PdfOptions.create();
      PdfConverter.getInstance().convert(document, out, options);

      LOGGER.info("PDF file created: " + pdfFileName);
    } catch (IOException e) {
      LOGGER.error("Could not convert word file " + wordFileName + " to pdf " + pdfFileName, e);
    }
  }

  private String createPdfFileName(String wordFileName) {
    return wordFileName.substring(0, wordFileName.lastIndexOf('.')).concat(EXTENSION_PDF);
  }
}
