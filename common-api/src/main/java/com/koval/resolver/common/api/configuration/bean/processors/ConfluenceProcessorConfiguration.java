package com.koval.resolver.common.api.configuration.bean.processors;


public class ConfluenceProcessorConfiguration {

  private String dataSetFile;
  private String vectorModelFile;
  private String confluenceMetadataFile;

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

  public String getConfluenceMetadataFile() {
    return confluenceMetadataFile;
  }

  public void setConfluenceMetadataFile(final String confluenceMetadataFile) {
    this.confluenceMetadataFile = confluenceMetadataFile;
  }

  @Override
  public String toString() {
    return "ConfluenceProcessorConfiguration{"
        + "dataSetFile='" + dataSetFile + '\''
        + ", vectorModelFile='" + vectorModelFile + '\''
        + ", confluenceMetadataFile='" + confluenceMetadataFile + '\''
        + '}';
  }
}
