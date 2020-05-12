package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class MetadataLineParser implements LineParser<DocMetadata> {

  private final String delimiter;

  public MetadataLineParser(final String delimiter) {
    this.delimiter = delimiter;
  }

  @Override
  public DocMetadata parseLine(final String line) {
    final String[] split = line.split(delimiter);

    final String key = split[0];
    final int fileIndex = Integer.parseInt(split[1]);
    final int pageNumber = Integer.parseInt(split[2]);

    return new DocMetadata(key, fileIndex, pageNumber);
  }
}
