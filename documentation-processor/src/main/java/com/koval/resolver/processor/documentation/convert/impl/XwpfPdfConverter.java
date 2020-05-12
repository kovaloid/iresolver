package com.koval.resolver.processor.documentation.convert.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class XwpfPdfConverter {

  public void convert(InputStream in, OutputStream out) throws IOException {
    final XWPFDocument document = new XWPFDocument(in);

    final PdfOptions options = PdfOptions.create();

    PdfConverter.getInstance().convert(document, out, options);
  }
}
