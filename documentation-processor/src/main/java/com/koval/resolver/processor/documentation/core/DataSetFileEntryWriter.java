package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;

import com.koval.resolver.common.api.util.TextUtil;

public class DataSetFileEntryWriter {

  void write(
          BufferedWriter dataSetBufferedWriter,
          String docPageKey,
          String docPageText,
          String delimiter
  ) throws IOException {
    dataSetBufferedWriter.write(docPageKey);
    dataSetBufferedWriter.write(delimiter);
    dataSetBufferedWriter.write(TextUtil.simplify(docPageText));
    dataSetBufferedWriter.newLine();
  }
}
