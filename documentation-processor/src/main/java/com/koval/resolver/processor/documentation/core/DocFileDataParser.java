package com.koval.resolver.processor.documentation.core;

import java.util.List;

import com.koval.resolver.processor.documentation.bean.DocFile;

public class DocFileDataParser {

  private static final String DELIMITER = " ";

  private final String docFileDataFileName;
  private final FileRepository fileRepository;

  public DocFileDataParser(
          final String docFileDataFileName,
          final FileRepository fileRepository
  ) {
    this.docFileDataFileName = docFileDataFileName;
    this.fileRepository = fileRepository;
  }

  public List<DocFile> parseDocumentationFilesList() {
    final FileParser fileParser = new FileParser(fileRepository);
    final DocDataLineParser docDataLineParser = new DocDataLineParser(DELIMITER);

    return fileParser.parseFile(docFileDataFileName, docDataLineParser);
  }
}
