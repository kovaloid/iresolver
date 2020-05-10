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
          DocumentationProcessorConfiguration configuration,
          DocOutputFilesParser docOutputFilesParser,
          VectorModel vectorModel,
          TextDataExtractor textDataExtractor
  ) {
    this.docOutputFilesParser = docOutputFilesParser;
    this.vectorModel = vectorModel;
    this.textDataExtractor = textDataExtractor;
    this.docsPath = configuration.getDocsFolder();
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);

    String extractedIssueText = textDataExtractor.extract(issue);
    Collection<String> similarDocKeys = vectorModel.getNearestLabels(
            extractedIssueText,
            NUMBER_OF_NEAREST_LABELS
    );
    LOGGER.info("Nearest doc keys for {}: {}", issue.getKey(), similarDocKeys);

    List<DocMetadata> docMetadata = docOutputFilesParser.parseDocumentationMetadata();
    List<DocFile> docFiles = docOutputFilesParser.parseDocumentationFilesList();

    List<DocumentationResult> similarDocs = similarDocKeys
            .stream()
            .map((String similarDocKey) -> getResultForKey(extractedIssueText, docMetadata, docFiles, similarDocKey))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

    result.setDocumentationResults(similarDocs);
  }

  private Optional<DocumentationResult> getResultForKey(
          String extractedIssueText,
          List<DocMetadata> docMetadata,
          List<DocFile> docFiles,
          String similarDocKey
  ) {
    return docMetadata.stream()
            .filter((DocMetadata metadata) -> metadata.getKey().equals(similarDocKey))
            .findFirst()
            .map((DocMetadata metadata) -> getResultForMetaData(extractedIssueText, docFiles, similarDocKey, metadata));
  }

  private DocumentationResult getResultForMetaData(
          String extractedIssueText,
          List<DocFile> docFiles,
          String similarDocKey,
          DocMetadata metadata
  ) {
    DocFile docFile = docFiles.stream()
            .filter((DocFile d) -> d.getFileIndex() == metadata.getFileIndex())
            .findFirst()
            .orElse(new DocFile(0, "no_such_file"));

    double similarity = vectorModel.similarityToLabel(extractedIssueText, similarDocKey);

    return new DocumentationResult(
            docFile.getFileName(),
            getFileUri(docsPath, docFile.getFileName()),
            metadata.getPageNumber(),
            Math.abs(similarity * 100)
    );
  }


  private String getFileUri(String path, String fileName) {
    return FileSystems.getDefault()
            .getPath(path, fileName)
            .toAbsolutePath()
            .normalize()
            .toUri()
            .toString();
  }
}
