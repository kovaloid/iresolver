package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DocMetadataParser {

  private static final String DELIMITER = " ";

  private static final Logger LOGGER = LoggerFactory.getLogger(DocMetadataParser.class);

  String docMetadataFileName;
  DocFileRepository docFileRepository;

  public DocMetadataParser(
          String docMetadataFileName,
          DocFileRepository docFileRepository
  ) {
    this.docMetadataFileName = docMetadataFileName;
    this.docFileRepository = docFileRepository;
  }

  public List<DocMetadata> parseDocumentationMetadata() {
    List<DocMetadata> docMetadataList = new ArrayList<>();

    try (
            InputStream fileInputStream = docFileRepository.getFile(docMetadataFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        DocMetadata docMetadata = parseLineIntoDocMetadata(line);
        docMetadataList.add(docMetadata);
      }
    } catch (IOException e) {
      LOGGER.error("Could not read file: " + docMetadataFileName, e);
    }

    return docMetadataList;
  }

  private DocMetadata parseLineIntoDocMetadata(String line) {
    String[] split = line.split(DELIMITER);

    String key = split[0];
    int fileIndex = Integer.parseInt(split[1]);
    int pageNumber = Integer.parseInt(split[2]);

    return new DocMetadata(key, fileIndex, pageNumber);
  }
}
