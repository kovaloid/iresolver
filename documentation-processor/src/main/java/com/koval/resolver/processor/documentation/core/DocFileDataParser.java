package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocFile;

import java.util.List;

public class DocFileDataParser {
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
    FileParser fileParser = new FileParser(docFileRepository);
    DocDataLineParser docDataLineParser = new DocDataLineParser();

    return fileParser.parseFile(docFileDataFileName, docDataLineParser);
  }
}
