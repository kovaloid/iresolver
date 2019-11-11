package com.koval.jresolver.docprocessor.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.util.TextUtil;
import com.koval.jresolver.docprocessor.configuration.DocumentationProcessorProperties;
import com.koval.jresolver.docprocessor.split.PageSplitter;


public class DocDataSetCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocDataSetCreator.class);
  private static final String KEY_PREFIX = "doc_";
  private static final String SPACE = " ";
  private static final String SEPARATOR = "|";

  private final DocTypeDetector docTypeDetector = new DocTypeDetector();
  private final DocumentationProcessorProperties properties;

  public DocDataSetCreator(DocumentationProcessorProperties properties) {
    this.properties = properties;
  }

  public void create() {
    File docsFolder = new File(properties.getDocsFolder());
    File[] docFiles = docsFolder.listFiles();
    if (docFiles == null) {
      LOGGER.warn("There are no documentation files");
      return;
    }

    int pageIndex = 0;
    int documentIndex = 0;

    for (final File docFile : docFiles) {
      if (docFile.isFile()) {
        try (InputStream inputFileStream = new BufferedInputStream(new FileInputStream(docFile))) {
          MediaType mediaType = docTypeDetector.detectType(inputFileStream, docFile.getName());
          if (docTypeDetector.isTypeSupported(mediaType)) {

            try (PrintWriter dataSetOutput = new PrintWriter(new File(properties.getWorkFolder(), properties.getDataSetFileName()), StandardCharsets.UTF_8.name());
                 PrintWriter metadataOutput = new PrintWriter(new File(properties.getWorkFolder(), "doc-metadata.txt"), StandardCharsets.UTF_8.name());
                 PrintWriter docListOutput = new PrintWriter(new File(properties.getWorkFolder(), "doc-list.txt"), StandardCharsets.UTF_8.name())) {

              PageSplitter pageSplitter = docTypeDetector.getFileParser(mediaType);
              Map<Integer, String> docPages = pageSplitter.getMapping(inputFileStream);

              for (Map.Entry<Integer, String> docPage : docPages.entrySet()) {
                String docPageKey = KEY_PREFIX + pageIndex;
                pageIndex++;

                dataSetOutput.print(docPageKey);
                dataSetOutput.print(SEPARATOR);
                dataSetOutput.println(TextUtil.simplify(docPage.getValue()));

                metadataOutput.print(docPageKey);
                metadataOutput.print(SPACE);
                metadataOutput.print(documentIndex);
                metadataOutput.print(SPACE);
                metadataOutput.println(docPage.getKey());
              }

              docListOutput.print(documentIndex);
              docListOutput.print(SPACE);
              docListOutput.println(docFile.getName());
              documentIndex++;
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
              LOGGER.error("Could not write to output file", e);
            }
          }
        } catch (FileNotFoundException e) {
          LOGGER.error("Could not find documentation file: " + docFile.getAbsolutePath(), e);
        } catch (IOException e) {
          LOGGER.error("Could not read documentation file" + docFile.getAbsolutePath(), e);
        }
      }
    }
  }
}
