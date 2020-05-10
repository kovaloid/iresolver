package com.koval.resolver.processor.documentation.core;

import java.io.*;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.util.TextUtil;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import com.koval.resolver.processor.documentation.split.PageSplitter;

public class DocDataSetEntryWriter {
  private static final Logger LOGGER = LoggerFactory.getLogger(DocDataSetEntryWriter.class);

  private static final String KEY_PREFIX = "doc_";
  private static final String SPACE = " ";
  private static final String SEPARATOR = "|";

  private final FileRepository fileRepository;
  private final PageSplitter pageSplitter;

  private int currentPageIndex;
  private int currentDocumentIndex;

  public DocDataSetEntryWriter(
          FileRepository fileRepository,
          PageSplitter pageSplitter
  ) {
    this.fileRepository = fileRepository;
    this.pageSplitter = pageSplitter;
  }

  //TODO: Come up with something better
  public void resetIndices() {
    currentPageIndex = 0;
    currentDocumentIndex = 0;
  }

  public void writeEntriesForDocFile(
          File docFile,
          BufferedWriter dataSetBufferedWriter,
          BufferedWriter metadataBufferedWriter,
          BufferedWriter docListBufferedWriter
  ) throws IOException {
    try (InputStream inputFileStream = new BufferedInputStream(fileRepository.readFile(docFile.getName())/*new FileInputStream(docFile)*/)) {
      Map<Integer, String> docPages = pageSplitter.getMapping(inputFileStream);

      writeEntriesForDocPages(
              docPages,
              dataSetBufferedWriter,
              metadataBufferedWriter
      );

      DocFile docFileData = new DocFile(currentDocumentIndex, docFile.getName());
      writeDocListFileEntry(docListBufferedWriter, docFileData);

      currentDocumentIndex++;
    } catch (FileNotFoundException e) {
      LOGGER.error("Could not find documentation file: " + docFile.getAbsolutePath(), e);
    }
  }

  private void writeEntriesForDocPages(
          Map<Integer, String> docPages,
          BufferedWriter dataSetBufferedWriter,
          BufferedWriter metadataBufferedWriter
  ) throws IOException {
    for (Map.Entry<Integer, String> docPage : docPages.entrySet()) {
      String docPageKey = KEY_PREFIX + currentPageIndex;
      currentPageIndex++;

      writeDataSetFileEntry(dataSetBufferedWriter, docPage, docPageKey);

      int docPageNumber = docPage.getKey();
      DocMetadata docMetadata = new DocMetadata(docPageKey, currentDocumentIndex, docPageNumber);
      writeMetadataFileEntry(metadataBufferedWriter, docMetadata);
    }
  }

  private void writeDocListFileEntry(
          BufferedWriter docListBufferedWriter,
          DocFile docFileData
  ) throws IOException {
    docListBufferedWriter.write(String.valueOf(docFileData.getFileIndex()));
    docListBufferedWriter.write(SPACE);
    docListBufferedWriter.write(docFileData.getFileName());
    docListBufferedWriter.newLine();
  }

  private void writeMetadataFileEntry(
          BufferedWriter metadataBufferedWriter,
          DocMetadata docMetadata
  ) throws IOException {
    metadataBufferedWriter.write(docMetadata.getKey());
    metadataBufferedWriter.write(SPACE);
    metadataBufferedWriter.write(String.valueOf(docMetadata.getFileIndex()));
    metadataBufferedWriter.write(SPACE);
    metadataBufferedWriter.write(String.valueOf(docMetadata.getPageNumber()));
    metadataBufferedWriter.newLine();
  }

  private void writeDataSetFileEntry(
          BufferedWriter dataSetBufferedWriter,
          Map.Entry<Integer, String> docPage,
          String docPageKey
  ) throws IOException {
    dataSetBufferedWriter.write(docPageKey);
    dataSetBufferedWriter.write(SEPARATOR);
    dataSetBufferedWriter.write(TextUtil.simplify(docPage.getValue()));
    dataSetBufferedWriter.newLine();
  }
}
