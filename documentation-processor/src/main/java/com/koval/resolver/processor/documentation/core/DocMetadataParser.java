package com.koval.resolver.processor.documentation.core;

import java.util.List;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class DocMetadataParser {

  private static final String DELIMITER = " ";

  private final String docMetadataFileName;
  private final DocFileRepository docFileRepository;

  public DocMetadataParser(
    final String docMetadataFileName,
    final DocFileRepository docFileRepository
  ) {
    this.docMetadataFileName = docMetadataFileName;
    this.docFileRepository = docFileRepository;
  }

  public List<DocMetadata> parseDocumentationMetadata() {
    FileParser fileParser = new FileParser(docFileRepository);
    MetadataLineParser metadataLineParser = new MetadataLineParser(DELIMITER);

    return fileParser.parseFile(docMetadataFileName, metadataLineParser);
  }
}
