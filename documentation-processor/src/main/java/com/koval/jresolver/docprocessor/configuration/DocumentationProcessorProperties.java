package com.koval.jresolver.docprocessor.configuration;

import com.koval.jresolver.common.api.doc2vec.Doc2VecProperties;

public class DocumentationProcessorProperties extends Doc2VecProperties {

  private String docsFolder;

  public DocumentationProcessorProperties() {
    super("documentation-processor.properties");
    this.docsFolder = properties.getProperty("docsFolder");
  }

  public String getDocsFolder() {
    return docsFolder;
  }

  public void setDocsFolder(String docsFolder) {
    this.docsFolder = docsFolder;
  }
}
