package com.koval.resolver.processor.documentation.core;

import java.io.BufferedWriter;
import java.io.IOException;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class MetadataFileEntryWriter {

  public void write(
          BufferedWriter metadataBufferedWriter,
          String docPageKey,
          int currentDocumentIndex,
          int docPageNumber,
          String delimiter
  ) throws IOException {
    final DocMetadata docMetadata = new DocMetadata(docPageKey, currentDocumentIndex, docPageNumber);

    writeMetadata(metadataBufferedWriter, docMetadata, delimiter);
  }

  private void writeMetadata(
          BufferedWriter metadataBufferedWriter,
          DocMetadata docMetadata,
          String delimiter
  ) throws IOException {
    metadataBufferedWriter.write(docMetadata.getKey());
    metadataBufferedWriter.write(delimiter);
    metadataBufferedWriter.write(String.valueOf(docMetadata.getFileIndex()));
    metadataBufferedWriter.write(delimiter);
    metadataBufferedWriter.write(String.valueOf(docMetadata.getPageNumber()));
    metadataBufferedWriter.newLine();
  }
}
