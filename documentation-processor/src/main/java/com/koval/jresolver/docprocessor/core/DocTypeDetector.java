package com.koval.jresolver.docprocessor.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.google.common.collect.Sets;
import com.koval.jresolver.docprocessor.split.PageSplitter;
import com.koval.jresolver.docprocessor.split.impl.PdfPageSplitter;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;

public class DocTypeDetector {

  private static final Set supportedTypes = Sets.newHashSet("application/pdf", "application/msword");

  public MediaType detectType(InputStream inputFileStream, String fileName) throws IOException {
    AutoDetectParser parser = new AutoDetectParser();
    Detector detector = parser.getDetector();
    Metadata md = new Metadata();
    md.add(Metadata.RESOURCE_NAME_KEY, fileName);
    return detector.detect(inputFileStream, md);
  }

  public boolean isTypeSupported(MediaType mediaType) {
    return supportedTypes.contains(mediaType.toString());
  }

  public PageSplitter getFileParser(MediaType mediaType) {
    if (mediaType.toString().equals("application/pdf")) {
      return new PdfPageSplitter();
    } else {
      throw new IllegalArgumentException("Unsupported format: " + mediaType.toString());
    }
  }
}
