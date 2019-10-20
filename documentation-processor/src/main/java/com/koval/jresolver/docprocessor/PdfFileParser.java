package com.koval.jresolver.docprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfFileParser implements FileParser {

  public Map<Integer, String> getMapping(InputStream input) throws IOException {
    Map<Integer, String> result = new HashMap<>();
    PDDocument doc = PDDocument.load(input);
    PDFTextStripper stripper = new PDFTextStripper();
    for (int i = 1; i <= doc.getNumberOfPages(); i++) {
      stripper.setStartPage(i);
      stripper.setEndPage(i);
      String pageText = stripper.getText(doc);
      result.put(i, pageText);
    }
    return result;
  }

}
