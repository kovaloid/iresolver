package com.koval.resolver.common.api.configuration.component.processors;


public class IssuesProcessorConfiguration {

  private String dataSetFile;
  private String vectorModelFile;
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

  public boolean isOverwriteMode() {
    return overwriteMode;
  }

  public void setOverwriteMode(final boolean overwriteMode) {
    this.overwriteMode = overwriteMode;
  }

  @Override
  public String toString() {
    return "IssuesProcessorConfiguration{"
        + "dataSetFile='" + dataSetFile + '\''
        + ", vectorModelFile='" + vectorModelFile + '\''
        + ", overwriteMode='" + overwriteMode + '\''
        + '}';
  }
}
