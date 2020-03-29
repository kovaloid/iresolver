package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


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
    List<DocMetadata> docMetadataList = new ArrayList<>();
    try (
            InputStream fileInputStream = new FileInputStream(properties.getDocsMetadataFile());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        DocMetadata docMetadata = parseLineIntoDocMetadata(line);
        docMetadataList.add(docMetadata);
      }
    } catch (IOException e) {
      LOGGER.error("Could not read file: " + properties.getDocsMetadataFile(), e);
    }

    return docMetadataList;
  }

  private DocMetadata parseLineIntoDocMetadata(String line) {
    String[] split = line.split(SPACE);

    String key = split[0];
    int fileIndex = Integer.parseInt(split[1]);
    int pageNumber = Integer.parseInt(split[2]);

    return new DocMetadata(key, fileIndex, pageNumber);
  }
}
