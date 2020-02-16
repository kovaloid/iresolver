package com.koval.resolver.processor.documentation.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;


public class DocOutputFilesParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocOutputFilesParser.class);
  private static final String SPACE = " ";

  private final DocumentationProcessorConfiguration properties;

  public DocOutputFilesParser(DocumentationProcessorConfiguration properties) {
    this.properties = properties;
  }

  public List<DocFile> parseDocumentationFilesList() {
    List<DocFile> docFiles = new ArrayList<>();
    try (InputStream in = new FileInputStream(properties.getDocsListFile());
         BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(SPACE);
        int fileIndex = Integer.parseInt(split[0]);
        String fileName = split[1];
        docFiles.add(new DocFile(fileIndex, fileName));
      }
    } catch (IOException e) {
      LOGGER.error("Could not read file: " + properties.getDocsListFile(), e);
    }
    return docFiles;
  }

  public List<DocMetadata> parseDocumentationMetadata() {
    List<DocMetadata> docMetadata = new ArrayList<>();
    try (InputStream in = new FileInputStream(properties.getDocsMetadataFile());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(SPACE);
        docMetadata.add(new DocMetadata(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
      }
    } catch (IOException e) {
      LOGGER.error("Could not read file: " + properties.getDocsMetadataFile(), e);
    }
    return docMetadata;
  }
}
