package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.DocumentationResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.resolver.processor.documentation.DocumentationProcessorDelegate;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentationProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationProcessorDelegate.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final VectorModel vectorModel;
  private final TextDataExtractor textDataExtractor = new TextDataExtractor();
  private final String docsPath;
  private final DocumentationProcessorConfiguration processorConfiguration;

  public DocumentationProcessor(Configuration properties) throws IOException {
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();
    File vectorModelFile = new File(properties.getProcessors().getDocumentation().getVectorModelFile());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile, properties.getParagraphVectors().getLanguage());
    this.docsPath = properties.getProcessors().getDocumentation().getDocsFolder();
    this.processorConfiguration = properties.getProcessors().getDocumentation();
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);

    Collection<String> similarDocKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue),
            NUMBER_OF_NEAREST_LABELS);
    LOGGER.info("Nearest doc keys for {}: {}", issue.getKey(), similarDocKeys);
    List<DocumentationResult> similarDocs = new ArrayList<>();
    DocOutputFilesParser docOutputFilesParser = new DocOutputFilesParser(
            processorConfiguration,
            new DocFileRepository()
    );
    List<DocMetadata> docMetadata = docOutputFilesParser.parseDocumentationMetadata();
    List<DocFile> docFiles = docOutputFilesParser.parseDocumentationFilesList();

    similarDocKeys.forEach((String similarDocKey) -> {
      docMetadata.stream()
              .filter((DocMetadata metadata) -> metadata.getKey().equals(similarDocKey))
              .findFirst()
              .map((DocMetadata metadata) -> {
                DocFile docFile = docFiles.stream()
                        .filter((DocFile d) -> d.getFileIndex() == metadata.getFileIndex())
                        .findFirst()
                        .orElse(new DocFile(0, "no_such_file"));
                double similarity = vectorModel.similarityToLabel(textDataExtractor.extract(issue), similarDocKey);
                return new DocumentationResult(
                        docFile.getFileName(),
                        getFileUri(docsPath, docFile.getFileName()),
                        metadata.getPageNumber(),
                        Math.abs(similarity * 100)
                );
              })
              .ifPresent(similarDocs::add);
    });

    result.setDocumentationResults(similarDocs);
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
