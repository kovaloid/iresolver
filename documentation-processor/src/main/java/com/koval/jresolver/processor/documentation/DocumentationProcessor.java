package com.koval.jresolver.processor.documentation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.DocumentationResult;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.common.api.doc2vec.TextDataExtractor;
import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.jresolver.processor.documentation.bean.DocFile;
import com.koval.jresolver.processor.documentation.bean.DocMetadata;
import com.koval.jresolver.processor.documentation.configuration.DocumentationProcessorProperties;
import com.koval.jresolver.processor.documentation.core.DocOutputFilesParser;


public class DocumentationProcessor implements IssueProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final VectorModel vectorModel;
  private final TextDataExtractor textDataExtractor = new TextDataExtractor();

  public DocumentationProcessor(DocumentationProcessorProperties properties) throws IOException {
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(properties);
    File vectorModelFile = new File(properties.getWorkFolder(), properties.getVectorModelFileName());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile);
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    setOriginalIssueToResults(issue, result);
    Collection<String> similarDocKeys = vectorModel.getNearestLabels(textDataExtractor.extract(issue),
        NUMBER_OF_NEAREST_LABELS);
    LOGGER.info("Nearest doc keys for {}: {}", issue.getKey(), similarDocKeys);
    List<DocumentationResult> similarDocs = new ArrayList<>();
    DocOutputFilesParser docOutputFilesParser = new DocOutputFilesParser();
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
                return new DocumentationResult(docFile.getFileName(), metadata.getPageNumber(), Math.abs(similarity * 100));
              })
              .ifPresent(similarDocs::add);
    });
    result.setDocumentationResults(similarDocs);
  }

}
