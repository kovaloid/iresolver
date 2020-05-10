package com.koval.resolver.processor.documentation.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.common.api.util.TextUtil;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;
import com.koval.resolver.processor.documentation.split.PageSplitter;
import com.koval.resolver.processor.documentation.split.impl.PdfPageSplitter;

public class DocDataSetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocDataSetCreator.class);

  private static final String KEY_PREFIX = "doc_";
  private static final String SPACE = " ";
  private static final String SEPARATOR = "|";

  private static final String EXTENSION_PDF = ".pdf";
  private static final String DOC_FILES_LOCATION = "../docs";

  private final DocTypeDetector docTypeDetector;
  private final PageSplitter pageSplitter = new PdfPageSplitter();
  private final FileConverter fileConverter;

  private final String docsFolderPath;

  private final DocumentationProcessorConfiguration properties;

  private int currentPageIndex;
  private int currentDocumentIndex;

  public DocDataSetCreator(
          DocumentationProcessorConfiguration properties,
          DocTypeDetector docTypeDetector,
          FileConverter fileConverter
  ) {
    this.docTypeDetector = docTypeDetector;
    this.fileConverter = fileConverter;

    docsFolderPath = properties.getDocsFolder();

    this.properties = properties;
  }

  //TODO: Refactor this method so we can test it safely
  public void create() throws IOException {
    File docsFolder = new File(docsFolderPath);
    File[] docFiles = docsFolder.listFiles();

    if (docFiles == null) {
      LOGGER.warn("There are no documentation files");
      return;
    }

    File dataSetFile = new File(properties.getDataSetFile());
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getCanonicalPath());
    LOGGER.info("Start creating data set file: {}", dataSetFile.getName());

    currentPageIndex = 0;
    currentDocumentIndex = 0;

    File docMetadataFile = new File(properties.getDocsMetadataFile());
    File docListFile = new File(properties.getDocsListFile());

    try (PrintWriter dataSetOutput = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name());
         PrintWriter metadataOutput = new PrintWriter(docMetadataFile, StandardCharsets.UTF_8.name());
         PrintWriter docListOutput = new PrintWriter(docListFile, StandardCharsets.UTF_8.name());
         BufferedWriter dataSetBufferedWriter = new BufferedWriter(dataSetOutput);
         BufferedWriter metadataBufferedWriter = new BufferedWriter(metadataOutput);
         BufferedWriter docListBufferedWriter = new BufferedWriter(docListOutput)) {

      for (final File docFile : docFiles) {
        if (docFile.isFile()) {
          MediaType mediaType = docTypeDetector.detectType(docFile.getName());

          if (mediaType.equals(MediaType.PDF)) {
            try (InputStream inputFileStream = new BufferedInputStream(new FileInputStream(docFile))) {
              writeEntriesForDocFile(
                      docFile,
                      inputFileStream,
                      dataSetBufferedWriter,
                      metadataBufferedWriter,
                      docListBufferedWriter
              );
            } catch (FileNotFoundException e) {
              LOGGER.error("Could not find documentation file: " + docFile.getAbsolutePath(), e);
            }
          }
        }
      }

      LOGGER.info("Data set file was created: {}", dataSetFile.getCanonicalPath());
      LOGGER.info("Doc metadata file was created: {}", docMetadataFile.getCanonicalPath());
      LOGGER.info("Doc list file was created: {}", docListFile.getCanonicalPath());
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      LOGGER.error("Could not write to output file", e);
    }
  }

  private void writeEntriesForDocFile(
          File docFile,
          InputStream inputFileStream,
          BufferedWriter dataSetBufferedWriter,
          BufferedWriter metadataBufferedWriter,
          BufferedWriter docListBufferedWriter
  ) throws IOException {
    Map<Integer, String> docPages = pageSplitter.getMapping(inputFileStream);

    writeEntriesForDocPages(
            docPages,
            dataSetBufferedWriter,
            metadataBufferedWriter
    );

    DocFile docFileData = new DocFile(currentDocumentIndex, docFile.getName());
    writeDocListFileEntry(docListBufferedWriter, docFileData);

    currentDocumentIndex++;
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

  public void convertWordFilesToPdf() {
    File docsFolder = new File(docsFolderPath);
    File[] docFiles = docsFolder.listFiles();

    if (docFiles == null) {
      LOGGER.warn("There are no documentation files");
      return;
    }

    for (final File docFile : docFiles) {
      if (docFile.isFile()) {
        MediaType mediaType = docTypeDetector.detectType(docFile.getName());
        if (mediaType.equals(MediaType.WORD)) {
          String wordFilePath = docFile.getName();
          String pdfFilePath = createPdfFilePath(wordFilePath);
          fileConverter.convert(wordFilePath, pdfFilePath);
        }
      }
    }
  }

  private String createPdfFilePath(String wordFilePath) {
    File pdfOutputFile = new File(DOC_FILES_LOCATION, replaceExtensionWithPdf(wordFilePath));

    return pdfOutputFile.getName();
  }

  private String replaceExtensionWithPdf(String wordFileName) {
    return wordFileName.substring(0, wordFileName.lastIndexOf('.')).concat(EXTENSION_PDF);
  }
}
