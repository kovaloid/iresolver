package com.koval.resolver.processor.documentation;

import java.nio.file.FileSystems;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.DocumentationResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import com.koval.resolver.processor.documentation.core.DocOutputFilesParser;


public class DocumentationProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final VectorModel vectorModel;
  private final TextDataExtractor textDataExtractor;
  private final String docsPath;

  private final DocOutputFilesParser docOutputFilesParser;

  public DocumentationProcessor(
    final DocumentationProcessorConfiguration configuration,
    final DocOutputFilesParser docOutputFilesParser,
    final VectorModel vectorModel,
    final TextDataExtractor textDataExtractor
  ) {
    this.docOutputFilesParser = docOutputFilesParser;
    this.vectorModel = vectorModel;
    this.textDataExtractor = textDataExtractor;
    this.docsPath = configuration.getDocsFolder();
  }

  @Override
  public void run(final Issue issue, final IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);

    final String extractedIssueText = textDataExtractor.extract(issue);
    final Collection<String> similarDocKeys = vectorModel.getNearestLabels(
            extractedIssueText,
            NUMBER_OF_NEAREST_LABELS
    );
    LOGGER.info("Nearest doc keys for {}: {}", issue.getKey(), similarDocKeys);

    final List<DocMetadata> docMetadata = docOutputFilesParser.parseDocumentationMetadata();
    final List<DocFile> docFiles = docOutputFilesParser.parseDocumentationFilesList();

    final List<DocumentationResult> similarDocs = similarDocKeys
            .stream()
            .map((String similarDocKey) -> getResultForKey(extractedIssueText, docMetadata, docFiles, similarDocKey))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

    result.setDocumentationResults(similarDocs);
  }

  private Optional<DocumentationResult> getResultForKey(
    final String extractedIssueText,
    final List<DocMetadata> docMetadata,
    final List<DocFile> docFiles,
    final String similarDocKey
  ) {
    return docMetadata.stream()
            .filter((DocMetadata metadata) -> metadata.getKey().equals(similarDocKey))
            .findFirst()
            .map((DocMetadata metadata) -> getResultForMetaData(extractedIssueText, docFiles, similarDocKey, metadata));
  }

  private DocumentationResult getResultForMetaData(
    final String extractedIssueText,
    final List<DocFile> docFiles,
    final String similarDocKey,
    final DocMetadata metadata
  ) {
    final DocFile docFile = docFiles.stream()
            .filter((DocFile d) -> d.getFileIndex() == metadata.getFileIndex())
            .findFirst()
            .orElse(new DocFile(0, "no_such_file"));

    final double similarity = vectorModel.similarityToLabel(extractedIssueText, similarDocKey);

    return new DocumentationResult(
            docFile.getFileName(),
            getFileUri(docsPath, docFile.getFileName()),
            metadata.getPageNumber(),
            Math.abs(similarity * 100)
    );
  }


  private String getFileUri(final String path, final String fileName) {
    return FileSystems.getDefault()
            .getPath(path, fileName)
            .toAbsolutePath()
            .normalize()
            .toUri()
            .toString();
  }
}
