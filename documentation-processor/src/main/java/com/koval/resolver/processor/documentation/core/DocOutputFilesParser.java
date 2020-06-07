package com.koval.resolver.processor.documentation.core;

import java.util.List;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;

public class DocOutputFilesParser {

  private final DocMetadataParser docMetadataParser;
  private final DocFileDataParser docFileDataParser;

  public DocOutputFilesParser(
          final DocumentationProcessorConfiguration properties,
          final FileRepository fileRepository
  ) {
    this.docMetadataParser = new DocMetadataParser(properties.getDocsMetadataFile(), fileRepository);
    this.docFileDataParser = new DocFileDataParser(properties.getDocsListFile(), fileRepository);
  }

  public List<DocFile> parseDocumentationFilesList() {
    return docFileDataParser.parseDocumentationFilesList();
  }


  public List<DocMetadata> parseDocumentationMetadata() {
    return docMetadataParser.parseDocumentationMetadata();
  }
}
