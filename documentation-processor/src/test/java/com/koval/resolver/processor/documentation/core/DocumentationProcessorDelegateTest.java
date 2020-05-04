package com.koval.resolver.processor.documentation.core;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.DocumentationResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.processor.documentation.bean.DocFile;
import com.koval.resolver.processor.documentation.bean.DocMetadata;

@ExtendWith(MockitoExtension.class)
public class DocumentationProcessorDelegateTest {
  private static final String DOCS_PATH = "docsPath";

  private static final String EXTRACTED_TEXT = "asdfasdfsadf";

  private static final List<String> NEAREST_LABELS = Arrays.asList("a");

  private static final List<DocMetadata> DOC_METADATA = Arrays.asList(
          new DocMetadata("a", 1, 5)
  );

  private static final List<DocFile> DOC_FILES = Arrays.asList(
          new DocFile(1, "filename")
  );

  private static final double SIMILARITY = 0.5;

  @Mock
  private DocOutputFilesParser docOutputFilesParser;

  @Mock
  private VectorModel vectorModel;

  @Mock
  private TextDataExtractor textDataExtractor;

  private DocumentationProcessorDelegate documentationProcessorDelegate;

  @BeforeEach
  void onSetup() {
    MockitoAnnotations.initMocks(this);

    documentationProcessorDelegate = new DocumentationProcessorDelegate(
            docOutputFilesParser,
            vectorModel,
            textDataExtractor,
            DOCS_PATH
    );

    when(textDataExtractor.extract(any(Issue.class))).thenReturn(EXTRACTED_TEXT);

    when(vectorModel.getNearestLabels(anyString(), anyInt())).thenReturn(NEAREST_LABELS);

    when(docOutputFilesParser.parseDocumentationMetadata()).thenReturn(DOC_METADATA);
    when(docOutputFilesParser.parseDocumentationFilesList()).thenReturn(DOC_FILES);

    when(vectorModel.similarityToLabel(anyString(), anyString())).thenReturn(SIMILARITY);
  }

  @Test
  void testGeneratingCorrectDocumentationResult() {
    Issue issue = new Issue();
    IssueAnalysingResult result = new IssueAnalysingResult();

    documentationProcessorDelegate.run(
            issue,
            result
    );

    List<DocumentationResult> documentationResults = result.getDocumentationResults();

    DocumentationResult documentationResult = documentationResults.get(0);

    assertEquals(DOC_FILES.get(0).getFileName(), documentationResult.getFileName());
    assertEquals(DOC_METADATA.get(0).getPageNumber(), documentationResult.getPageNumber());
    //TODO: get rid of this implementation detail
    assertEquals(Math.abs(SIMILARITY * 100), documentationResult.getRank());

    assertEquals(issue, result.getOriginalIssue());
  }

}
