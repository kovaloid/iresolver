package com.koval.resolver.processor.documentation.core;

import java.util.List;

import com.koval.resolver.processor.documentation.bean.DocFile;

public class DocFileDataParser {
  private static final String DELIMITER = " ";

  private final String docFileDataFileName;
  private final DocFileRepository docFileRepository;

  public DocFileDataParser(
          String docFileDataFileName,
          DocFileRepository docFileRepository
  ) {
    this.docFileDataFileName = docFileDataFileName;
    this.docFileRepository = docFileRepository;
  }

  public List<DocFile> parseDocumentationFilesList() {
    FileParser fileParser = new FileParser(docFileRepository);
    DocDataLineParser docDataLineParser = new DocDataLineParser(DELIMITER);

    return fileParser.parseFile(docFileDataFileName, docDataLineParser);
  }
}
