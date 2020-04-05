package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocFile;

public class DocDataLineParser implements LineParser<DocFile> {
  private static final String DELIMITER = " ";

  @Override
  public DocFile parseLine(String line) {
    String[] split = line.split(DELIMITER);

    int fileIndex = Integer.parseInt(split[0]);
    String fileName = split[1];

    return new DocFile(fileIndex, fileName);
  }
}

