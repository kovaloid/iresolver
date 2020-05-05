package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class MetadataLineParser implements LineParser<DocMetadata> {

  private final String delimiter;

  MetadataLineParser(String delimiter) {
    this.delimiter = delimiter;
  }

  @Override
  public DocMetadata parseLine(String line) {
    String[] split = line.split(delimiter);

    String key = split[0];
    int fileIndex = Integer.parseInt(split[1]);
    int pageNumber = Integer.parseInt(split[2]);

    return new DocMetadata(key, fileIndex, pageNumber);
  }
}
