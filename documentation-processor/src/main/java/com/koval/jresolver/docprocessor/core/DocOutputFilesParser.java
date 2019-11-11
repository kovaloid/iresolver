package com.koval.jresolver.docprocessor.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.docprocessor.bean.DocFile;
import com.koval.jresolver.docprocessor.bean.DocMetadata;


public class DocOutputFilesParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocOutputFilesParser.class);
  private static final String SPACE = " ";
  /*
  private DocumentationProcessorProperties properties;

  public DocOutputFilesParser(DocumentationProcessorProperties properties) {
    this.properties = properties;
  }
  */

  public List<DocFile> parseDocumentationFilesList() {
    List<DocFile> docFiles = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("doc-list.txt"), StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(SPACE);
        int fileIndex = Integer.parseInt(split[0]);
        String fileName = split[1];
        docFiles.add(new DocFile(fileIndex, fileName));
      }
    } catch (IOException e) {
      LOGGER.error("Could not read doc-list.txt file", e);
    }
    return docFiles;
  }

  public List<DocMetadata> parseDocumentationMetadata() {
    List<DocMetadata> docMetadata = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("doc-metadata.txt"), StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(SPACE);
        docMetadata.add(new DocMetadata(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
      }
    } catch (IOException e) {
      LOGGER.error("Could not read doc-metadata.txt file", e);
    }
    return docMetadata;
  }
}
