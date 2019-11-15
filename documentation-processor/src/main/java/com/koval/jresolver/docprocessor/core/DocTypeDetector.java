package com.koval.jresolver.docprocessor.core;

import com.koval.jresolver.docprocessor.split.PageSplitter;
import com.koval.jresolver.docprocessor.split.impl.PdfPageSplitter;
import com.koval.jresolver.docprocessor.split.impl.WordPageSplitter;


public class DocTypeDetector {

  public MediaType detectType(String fileName) {
    if (fileName.endsWith(".pdf")) {
      return MediaType.PDF;
    } else if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
      return MediaType.WORD;
    }
    return MediaType.UNKNOWN;
  }

  public boolean isTypeSupported(MediaType mediaType) {
    for (MediaType supportedMediaType: MediaType.values()) {
      if (mediaType.equals(supportedMediaType)) {
        return true;
      }
    }
    return false;
  }

  public PageSplitter getFileParser(MediaType mediaType) {
    if (mediaType.equals(MediaType.PDF)) {
      return new PdfPageSplitter();
    } else if (mediaType.equals(MediaType.WORD)) {
      return new WordPageSplitter();
    } else {
      throw new IllegalArgumentException("Unsupported format: " + mediaType.toString());
    }
  }
}
