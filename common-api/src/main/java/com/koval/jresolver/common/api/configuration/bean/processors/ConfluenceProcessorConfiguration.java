package com.koval.jresolver.common.api.configuration.bean.processors;


public class ConfluenceProcessorConfiguration {

  private String dataSetFile;
  private String vectorModelFile;
  private String confluenceMetadataFile;

  public String getDataSetFile() {
    return dataSetFile;
  }

  public void setDataSetFile(String dataSetFile) {
    this.dataSetFile = dataSetFile;
  }

  public String getVectorModelFile() {
    return vectorModelFile;
  }

  public void setVectorModelFile(String vectorModelFile) {
    this.vectorModelFile = vectorModelFile;
  }

  public String getConfluenceMetadataFile() {
    return confluenceMetadataFile;
  }

  public void setConfluenceMetadataFile(String confluenceMetadataFile) {
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
