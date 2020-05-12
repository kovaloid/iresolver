package com.koval.resolver.processor.documentation.split.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.koval.resolver.processor.documentation.split.PageSplitter;


public class PdfPageSplitter implements PageSplitter {

  @Override
  public Map<Integer, String> getMapping(final InputStream input) throws IOException {
    final Map<Integer, String> result = new HashMap<>();
    final PDDocument doc = PDDocument.load(input);
    final PDFTextStripper stripper = new PDFTextStripper();

    for (int i = 1; i <= doc.getNumberOfPages(); i++) {
      stripper.setStartPage(i);
      stripper.setEndPage(i);
      final String pageText = stripper.getText(doc);
      result.put(i, pageText);
    }

    return result;
  }
}
