package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;

import com.koval.resolver.processor.documentation.bean.DocFile;

public class DocListFileEntryWriter {

  void write(
          BufferedWriter docListBufferedWriter,
          int fileIndex,
          String fileName,
          String delimiter
  ) throws IOException {
    final DocFile docFile = new DocFile(fileIndex, fileName);
    writeDocFileEntry(docListBufferedWriter, docFile, delimiter);
  }

  private void writeDocFileEntry(
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
