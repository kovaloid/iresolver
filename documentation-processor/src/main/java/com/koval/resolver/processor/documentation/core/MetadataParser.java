package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class MetadataParser implements LineParser<DocMetadata> {
  private static final String DELIMITER = " ";

  @Override
  public DocMetadata parseLine(String line) {
    String[] split = line.split(DELIMITER);

    String key = split[0];
    int fileIndex = Integer.parseInt(split[1]);
    int pageNumber = Integer.parseInt(split[2]);

    return new DocMetadata(key, fileIndex, pageNumber);
  }
}
