package com.koval.jresolver.docprocessor.core;

import com.koval.jresolver.docprocessor.split.PageSplitter;
import com.koval.jresolver.docprocessor.split.impl.PdfPageSplitter;


public class DocTypeDetector {

  public MediaType detectType(String fileName) {
    if (fileName.endsWith(".pdf")) {
      return MediaType.PDF;
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
    } else {
      throw new IllegalArgumentException("Unsupported format: " + mediaType.toString());
    }
  }
}
