package com.koval.resolver.common.api.configuration.bean.processors;


public class IssuesProcessorConfiguration {

  private String dataSetFile;
  private boolean overwriteMode;
  private String vectorModelFile;

  public String getDataSetFile() {
    return dataSetFile;
  }

  public void setDataSetFile(final String dataSetFile) {
    this.dataSetFile = dataSetFile;
  }

  public String getVectorModelFile() {
    return vectorModelFile;
  }

  public boolean isOverwriteMode() {
    return overwriteMode;
  }

  public void setOverwriteMode(final boolean overwriteMode) {
    this.overwriteMode = overwriteMode;
  }

  public void setVectorModelFile(final String vectorModelFile) {
    this.vectorModelFile = vectorModelFile;
  }

  @Override
  public String toString() {
    return "IssuesProcessorConfiguration{"
        + "dataSetFile='" + dataSetFile + '\''
        + ", overwriteMode='" + overwriteMode + '\''
        + ", vectorModelFile='" + vectorModelFile + '\''
        + '}';
  }
}
