package com.koval.resolver.processor.documentation.core;

import java.util.List;

import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class DocMetadataParser {

  private static final String DELIMITER = " ";

  private final String docMetadataFileName;
  private final FileRepository fileRepository;

  public DocMetadataParser(
          final String docMetadataFileName,
          final FileRepository fileRepository
  ) {
    this.docMetadataFileName = docMetadataFileName;
    this.fileRepository = fileRepository;
  }

  public List<DocMetadata> parseDocumentationMetadata() {
    final FileParser fileParser = new FileParser(fileRepository);
    final MetadataLineParser metadataLineParser = new MetadataLineParser(DELIMITER);

    return fileParser.parseFile(docMetadataFileName, metadataLineParser);
  }
}
