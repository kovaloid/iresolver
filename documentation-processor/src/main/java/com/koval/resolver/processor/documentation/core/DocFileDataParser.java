package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DocFileDataParser {
  private static final Logger LOGGER = LoggerFactory.getLogger(DocOutputFilesParser.class);

  private static final String DELIMITER = " ";

  private String docFileDataFileName;
  private DocFileRepository docFileRepository;

  public DocFileDataParser(
          String docFileDataFileName,
          DocFileRepository docFileRepository
  ) {
    this.docFileDataFileName = docFileDataFileName;
    this.docFileRepository = docFileRepository;
  }

  public List<DocFile> parseDocumentationFilesList() {
    List<DocFile> docFiles = new ArrayList<>();
    try (
            InputStream fileInputStream = docFileRepository.getFile(docFileDataFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        DocFile docFile = parseLineIntoDocFile(line);
        docFiles.add(docFile);
      }
    } catch (IOException e) {
      LOGGER.error("Could not read file: " + docFileDataFileName, e);
    }
    return docFiles;
  }

  private DocFile parseLineIntoDocFile(String line) {
    String[] split = line.split(DELIMITER);
    int fileIndex = Integer.parseInt(split[0]);
    String fileName = split[1];
    return new DocFile(fileIndex, fileName);
  }
}
