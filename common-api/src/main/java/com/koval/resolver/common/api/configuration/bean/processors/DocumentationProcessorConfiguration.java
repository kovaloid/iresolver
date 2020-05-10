package com.koval.resolver.common.api.configuration.bean.processors;


public class DocumentationProcessorConfiguration {

  private String dataSetFile;
  private String vectorModelFile;
  private String docsMetadataFile;
  private String docsListFile;
  private String docsFolder;

  public String getDataSetFile() {
    return dataSetFile;
  }

  public void setDataSetFile(final String dataSetFile) {
    this.dataSetFile = dataSetFile;
  }

  public String getVectorModelFile() {
    return vectorModelFile;
  }

  public void setVectorModelFile(final String vectorModelFile) {
    this.vectorModelFile = vectorModelFile;
  }

  public String getDocsMetadataFile() {
    return docsMetadataFile;
  }

  public void setDocsMetadataFile(final String docsMetadataFile) {
    this.docsMetadataFile = docsMetadataFile;
  }

  public String getDocsListFile() {
    return docsListFile;
  }

  public void setDocsListFile(final String docsListFile) {
    this.docsListFile = docsListFile;
  }

  public String getDocsFolder() {
    return docsFolder;
  }

  public void setDocsFolder(final String docsFolder) {
    this.docsFolder = docsFolder;
  }

  @Override
  public String toString() {
    return "DocumentationProcessorConfiguration{"
        + "dataSetFile='" + dataSetFile + '\''
        + ", vectorModelFile='" + vectorModelFile + '\''
        + ", docsMetadataFile='" + docsMetadataFile + '\''
        + ", docsListFile='" + docsListFile + '\''
        + ", docsFolder='" + docsFolder + '\''
        + '}';
  }
}
