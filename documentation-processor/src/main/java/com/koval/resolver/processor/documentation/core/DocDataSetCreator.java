package com.koval.resolver.processor.documentation.core;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;

public class DocDataSetCreator {
  private static final Logger LOGGER = LoggerFactory.getLogger(DocDataSetCreator.class);

  private static final String EXTENSION_PDF = ".pdf";
  private static final String DOC_FILES_LOCATION = "../docs";

  private final DocDataSetEntryWriter docDataSetEntryWriter;

  private final DocTypeDetector docTypeDetector;

  private final FileConverter fileConverter;

  private final String docsFolderPath;

  private final DocumentationProcessorConfiguration properties;

  public DocDataSetCreator(
          DocumentationProcessorConfiguration properties,
          DocDataSetEntryWriter docDataSetEntryWriter,
          DocTypeDetector docTypeDetector,
          FileConverter fileConverter
  ) {
    this.properties = properties;
    this.docDataSetEntryWriter = docDataSetEntryWriter;
    this.docTypeDetector = docTypeDetector;
    this.fileConverter = fileConverter;

    docsFolderPath = properties.getDocsFolder();
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

    docDataSetEntryWriter.resetIndices();

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
            docDataSetEntryWriter.writeEntriesForDocFile(
                    docFile,
                    dataSetBufferedWriter,
                    metadataBufferedWriter,
                    docListBufferedWriter
            );
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
