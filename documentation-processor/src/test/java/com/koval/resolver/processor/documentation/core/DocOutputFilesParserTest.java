package com.koval.resolver.processor.documentation.core;

import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import org.junit.jupiter.api.BeforeEach;

class DocOutputFilesParserTest {

  private static final DocumentationProcessorConfiguration PROPERTIES = new DocumentationProcessorConfiguration();

  private DocOutputFilesParser mDocOutputFilesParser;

  @BeforeEach
  private void onSetup() {
    mDocOutputFilesParser = new DocOutputFilesParser(PROPERTIES);
  }

}
