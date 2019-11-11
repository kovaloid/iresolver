package com.koval.jresolver.docprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.koval.jresolver.common.api.bean.doc.Documentation;
import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;
import com.koval.jresolver.common.api.doc2vec.TextDataExtractor;
import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.jresolver.docprocessor.bean.DocFile;
import com.koval.jresolver.docprocessor.bean.DocMetadata;
import com.koval.jresolver.docprocessor.configuration.DocumentationProcessorProperties;
import com.koval.jresolver.docprocessor.core.DocOutputFilesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    List<Documentation> similarDocs = new ArrayList<>();
    DocOutputFilesParser docOutputFilesParser = new DocOutputFilesParser();
    List<DocMetadata> docMetadata = docOutputFilesParser.parseDocumentationMetadata();
    List<DocFile> docFiles = docOutputFilesParser.parseDocumentationFilesList();

    similarDocKeys.forEach((String similarDocKey) -> docMetadata.stream()
        .filter((DocMetadata metadata) -> metadata.getKey().equals(similarDocKey))
        .findFirst()
        .map((DocMetadata metadata) -> {
          DocFile docFile = docFiles.stream()
              .filter((DocFile d) -> d.getFileIndex() == metadata.getFileIndex())
              .findFirst()
              .orElse(new DocFile(0, "no_such_file"));
          return new Documentation(docFile.getFileName(), metadata.getPageNumber());
        })
        .ifPresent(similarDocs::add));
    result.setDocumentations(similarDocs);
  }

}
