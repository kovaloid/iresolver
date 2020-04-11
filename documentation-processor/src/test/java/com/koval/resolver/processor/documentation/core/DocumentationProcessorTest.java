package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.DocumentationResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentationProcessorTest {
  private String docsPath = "docsPath";

  private String extractedText = "asdfasdfsadf";

  private List<String> nearestLabels = Arrays.asList("a");

  private List<DocMetadata> metadata = Arrays.asList(
          new DocMetadata("a", 1, 5)
  );

  private List<DocFile> docFiles = Arrays.asList(
          new DocFile(1, "filename")
  );

  private double similarity = 0.5;

  @Mock
  DocOutputFilesParser mDocOutputFilesParser;

  @Mock
  VectorModel mVectorModel;

  @Mock
  TextDataExtractor mTextDataExtractor;

  private DocumentationProcessor mDocumentationProcessor;

  @BeforeEach
  void onSetup() throws IOException {
    MockitoAnnotations.initMocks(this);

    Configuration configuration = new Configuration();
    mDocumentationProcessor = new DocumentationProcessor(
            configuration,
            mDocOutputFilesParser,
            mVectorModel,
            mTextDataExtractor,
            docsPath
    );

    when(mTextDataExtractor.extract(any(Issue.class))).thenReturn(extractedText);

    when(mVectorModel.getNearestLabels(anyString(), anyInt())).thenReturn(nearestLabels);

    when(mDocOutputFilesParser.parseDocumentationMetadata()).thenReturn(metadata);
    when(mDocOutputFilesParser.parseDocumentationFilesList()).thenReturn(docFiles);

    when(mVectorModel.similarityToLabel(anyString(), anyString())).thenReturn(similarity);
  }

  @Test
  void testGeneratingCorrectDocumentationResult() {
    Issue issue = new Issue();
    IssueAnalysingResult result = new IssueAnalysingResult();

    mDocumentationProcessor.run(
            issue,
            result
    );

    List<DocumentationResult> documentationResults = result.getDocumentationResults();

    DocumentationResult documentationResult = documentationResults.get(0);

    assertEquals(docFiles.get(0).getFileName(), documentationResult.getFileName());
    assertEquals(metadata.get(0).getPageNumber(), documentationResult.getPageNumber());
    //TODO: get rid of this implementation detail
    assertEquals(Math.abs(similarity * 100), documentationResult.getRank());

    assertEquals(issue, result.getOriginalIssue());
  }

}
