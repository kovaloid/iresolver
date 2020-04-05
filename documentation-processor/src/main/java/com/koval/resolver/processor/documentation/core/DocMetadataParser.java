package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

import java.util.List;

public class DocMetadataParser {
  private static final String DELIMITER = " ";

  private String docMetadataFileName;
  private DocFileRepository docFileRepository;

  public DocMetadataParser(
          String docMetadataFileName,
          DocFileRepository docFileRepository
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
