package com.koval.resolver.processor.documentation;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.processor.IssueProcessor;
import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.processor.documentation.core.DocumentationProcessor;

import java.io.IOException;


public class DocumentationProcessorDelegate implements IssueProcessor {
  private DocumentationProcessor mDocumentationProcessor;

  public DocumentationProcessorDelegate(Configuration properties) throws IOException {
    mDocumentationProcessor = new DocumentationProcessor(properties);
  }

  @Override
  public void run(Issue issue, IssueAnalysingResult result) {
    mDocumentationProcessor.run(issue, result);
  }
}
