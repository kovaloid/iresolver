package com.koval.resolver.processor.documentation.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.MediaType;
import com.koval.resolver.processor.documentation.convert.FileConverter;


public class DocDataSetCreator {
  private static final Logger LOGGER = LoggerFactory.getLogger(DocDataSetCreator.class);

  private static final String EXTENSION_PDF = ".pdf";

  private final DocDataSetEntryWriter docDataSetEntryWriter;

  private final DocTypeDetector docTypeDetector;

  private final Map<MediaType, FileConverter> fileConverters;

  private final String docsFolderPath;

  private final DocumentationProcessorConfiguration properties;

  public DocDataSetCreator(
          final DocumentationProcessorConfiguration properties,
          final DocDataSetEntryWriter docDataSetEntryWriter,
          final DocTypeDetector docTypeDetector,
          final Map<MediaType, FileConverter> fileConverters
  ) {
    this.properties = properties;
    this.docDataSetEntryWriter = docDataSetEntryWriter;
    this.docTypeDetector = docTypeDetector;
    this.fileConverters = fileConverters;
    this.docsFolderPath = properties.getDocsFolder();
  }


  //TODO: Refactor this method so we can test it safely
  public void create() throws IOException {
    final File docsFolder = new File(docsFolderPath);
    final File[] docFiles = docsFolder.listFiles();

    if (docFiles == null) {
      LOGGER.warn("There are no documentation files");
      return;
    }

    final File dataSetFile = new File(properties.getDataSetFile());
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getCanonicalPath());
    LOGGER.info("Start creating data set file: {}", dataSetFile.getName());

    docDataSetEntryWriter.resetIndices();

    final File docMetadataFile = new File(properties.getDocsMetadataFile());
    final File docListFile = new File(properties.getDocsListFile());

    try (PrintWriter dataSetOutput = new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name());
         PrintWriter metadataOutput = new PrintWriter(docMetadataFile, StandardCharsets.UTF_8.name());
         PrintWriter docListOutput = new PrintWriter(docListFile, StandardCharsets.UTF_8.name());
         BufferedWriter dataSetBufferedWriter = new BufferedWriter(dataSetOutput);
         BufferedWriter metadataBufferedWriter = new BufferedWriter(metadataOutput);
         BufferedWriter docListBufferedWriter = new BufferedWriter(docListOutput)) {

      for (final File docFile : docFiles) {
        if (docFile.isFile()) {
          final MediaType mediaType = docTypeDetector.detectType(docFile.getName());

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


  public void convert() {
    final File docsFolder = new File(docsFolderPath);
    final File[] docFiles = docsFolder.listFiles();
    if (docFiles == null) {
      LOGGER.warn("There are no documentation files");
      return;
    }
    for (final File docFile : docFiles) {
      if (docFile.isFile()) {
          final MediaType mediaType = docTypeDetector.detectType(docFile.getName());
          if (fileConverters.containsKey(mediaType)) {
            fileConverters.get(mediaType).convert(docFile.getAbsolutePath(), createPdfFilePath(docFile.getName()));
          } else {
            LOGGER.warn("No converter found for document: " + docFile.getName());
          }
      }
    }
  }


  private String createPdfFilePath(String wordFilePath) {
    final File pdfOutputFile = new File(docsFolderPath, replaceExtensionWithPdf(wordFilePath));

    return pdfOutputFile.getAbsolutePath();
  }

  private String replaceExtensionWithPdf(String wordFileName) {
    return wordFileName.substring(0, wordFileName.lastIndexOf('.')).concat(EXTENSION_PDF);
  }

  public String getDocsFolderPath() {
    return docsFolderPath;
  }
}
