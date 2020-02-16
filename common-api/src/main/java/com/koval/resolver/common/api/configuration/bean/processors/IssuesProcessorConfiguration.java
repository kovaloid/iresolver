package com.koval.resolver.common.api.configuration.bean.processors;


public class IssuesProcessorConfiguration {

  private String dataSetFile;
  private String vectorModelFile;

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

  @Override
  public String toString() {
    return "IssuesProcessorConfiguration{"
        + "dataSetFile='" + dataSetFile + '\''
        + ", vectorModelFile='" + vectorModelFile + '\''
        + '}';
  }
}
