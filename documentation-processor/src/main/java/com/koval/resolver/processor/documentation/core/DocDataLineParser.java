package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocFile;

public class DocDataLineParser implements LineParser<DocFile> {

  private final String delimiter;

  public DocDataLineParser(final String delimiter) {
    this.delimiter = delimiter;
  }

  @Override
  public DocFile parseLine(final String line) {
    final String[] split = line.split(delimiter);

    final int fileIndex = Integer.parseInt(split[0]);
    final String fileName = split[1];

    return new DocFile(fileIndex, fileName);
  }
}

