package com.koval.jresolver.processor.documentation.configuration;

import com.koval.jresolver.common.api.doc2vec.Doc2VecProperties;


public class DocumentationProcessorProperties extends Doc2VecProperties {

  private String docsFolder;

  public DocumentationProcessorProperties() {
    super("documentation-processor.properties");
    this.docsFolder = getProperties().getProperty("docsFolder");
  }

  public String getDocsFolder() {
    return docsFolder;
  }

  public void setDocsFolder(String docsFolder) {
    this.docsFolder = docsFolder;
  }
}
