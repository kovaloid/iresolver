package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.common.api.doc2vec.TextDataExtractor;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentationProcessorTest {

  private String vectorModelFilePath = "lol";
  private String vectorModelLanguage = "English";
  private String docsPath = "docsPath";

  private String extractedText = "asdfasdfsadf";

  private List<String> nearestLabels = Arrays.asList("a", "b");

  private DocumentationProcessorConfiguration mDocumentationProcessorConfiguration;

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

  }

  @Test
  void testTrue() {
    Issue issue = new Issue();
    IssueAnalysingResult result = new IssueAnalysingResult();

    mDocumentationProcessor.run(
            issue,
            result
    );
  }

}
