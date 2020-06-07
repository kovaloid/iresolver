package com.koval.resolver.processor.documentation.core;

import java.io.*;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.processor.documentation.split.PageSplitter;

public class DocDataSetEntryWriter {
  private static final Logger LOGGER = LoggerFactory.getLogger(DocDataSetEntryWriter.class);

  private static final String KEY_PREFIX = "doc_";
  private static final String DELIMITER = " ";
  private static final String DATA_SET_SEPARATOR = "|";

  private final FileRepository fileRepository;
  private final PageSplitter pageSplitter;

  private final MetadataFileEntryWriter metadataFileEntryWriter;
  private final DocListFileEntryWriter docListFileEntryWriter;
  private final DataSetFileEntryWriter dataSetFileEntryWriter;

  private int currentPageIndex;
  private int currentDocumentIndex;

  public DocDataSetEntryWriter(
          FileRepository fileRepository,
          PageSplitter pageSplitter,
          MetadataFileEntryWriter metadataFileEntryWriter,
          DocListFileEntryWriter docListFileEntryWriter,
          DataSetFileEntryWriter dataSetFileEntryWriter
  ) {
    this.fileRepository = fileRepository;
    this.pageSplitter = pageSplitter;

    this.metadataFileEntryWriter = metadataFileEntryWriter;
    this.docListFileEntryWriter = docListFileEntryWriter;
    this.dataSetFileEntryWriter = dataSetFileEntryWriter;
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
    try (InputStream inputFileStream = new BufferedInputStream(fileRepository.readFile(docFile.getCanonicalPath()))) {
      final Map<Integer, String> docPages = pageSplitter.getMapping(inputFileStream);

      writeEntriesForDocPages(
              docPages,
              dataSetBufferedWriter,
              metadataBufferedWriter
      );

      docListFileEntryWriter.write(docListBufferedWriter, currentDocumentIndex, docFile.getName(), DELIMITER);

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
    for (final Map.Entry<Integer, String> docPage : docPages.entrySet()) {
      final String docPageKey = KEY_PREFIX + currentPageIndex;
      currentPageIndex++;

      dataSetFileEntryWriter.write(dataSetBufferedWriter, docPageKey, docPage.getValue(), DATA_SET_SEPARATOR);

      final int docPageNumber = docPage.getKey();

      metadataFileEntryWriter.write(metadataBufferedWriter, docPageKey, currentDocumentIndex, docPageNumber, DELIMITER);
    }
  }
}
