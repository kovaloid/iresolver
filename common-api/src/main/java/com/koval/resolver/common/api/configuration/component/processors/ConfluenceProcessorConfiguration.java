package com.koval.resolver.common.api.configuration.component.processors;


public class ConfluenceProcessorConfiguration {

  private String dataSetFile;
  private String vectorModelFile;
  private String confluenceMetadataFile;
  private boolean overwriteMode;

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

  public boolean isOverwriteMode() {
    return overwriteMode;
  }

  public void setOverwriteMode(final boolean overwriteMode) {
    this.overwriteMode = overwriteMode;
  }

  @Override
  public String toString() {
    return "ConfluenceProcessorConfiguration{"
        + "dataSetFile='" + dataSetFile + '\''
        + ", vectorModelFile='" + vectorModelFile + '\''
        + ", confluenceMetadataFile='" + confluenceMetadataFile + '\''
        + ", overwriteMode='" + overwriteMode + '\''
        + '}';
  }
}
