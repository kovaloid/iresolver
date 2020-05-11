package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;

import com.koval.resolver.processor.documentation.bean.DocFile;

public class DocListFileEntryWriter {

  void write(
          BufferedWriter docListBufferedWriter,
          DocFile docFileData,
          String delimiter
  ) throws IOException {
    docListBufferedWriter.write(String.valueOf(docFileData.getFileIndex()));
    docListBufferedWriter.write(delimiter);
    docListBufferedWriter.write(docFileData.getFileName());
    docListBufferedWriter.newLine();
  }
}