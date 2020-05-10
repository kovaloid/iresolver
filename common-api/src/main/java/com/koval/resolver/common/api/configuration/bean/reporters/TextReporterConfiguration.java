package com.koval.resolver.common.api.configuration.bean.reporters;


public class TextReporterConfiguration {

  private String outputFile;

  public String getOutputFile() {
    return outputFile;
  }

  public void setOutputFile(final String outputFile) {
    this.outputFile = outputFile;
  }

  @Override
  public String toString() {
    return "TextReporterConfiguration{"
        + "outputFile='" + outputFile + '\''
        + '}';
  }
}
