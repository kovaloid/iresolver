package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import com.koval.resolver.common.api.util.TextUtil;

public class DataSetFileEntryWriter {

  void write(
          BufferedWriter dataSetBufferedWriter,
          Map.Entry<Integer, String> docPage,
          String docPageKey,
          String delimiter
  ) throws IOException {
    dataSetBufferedWriter.write(docPageKey);
    dataSetBufferedWriter.write(delimiter);
    dataSetBufferedWriter.write(TextUtil.simplify(docPage.getValue()));
    dataSetBufferedWriter.newLine();
  }
}