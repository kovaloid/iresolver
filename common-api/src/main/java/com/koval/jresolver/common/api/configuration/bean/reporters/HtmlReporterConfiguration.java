package com.koval.jresolver.common.api.configuration.bean.reporters;


public class HtmlReporterConfiguration {

  private boolean openBrowser;
  private String htmlTemplateFile;
  private String outputFile;

  public boolean isOpenBrowser() {
    return openBrowser;
  }

  public void setOpenBrowser(boolean openBrowser) {
    this.openBrowser = openBrowser;
  }

  public String getHtmlTemplateFile() {
    return htmlTemplateFile;
  }

  public void setHtmlTemplateFile(String htmlTemplateFile) {
    this.htmlTemplateFile = htmlTemplateFile;
  }

  public String getOutputFile() {
    return outputFile;
  }

  public void setOutputFile(String outputFile) {
    this.outputFile = outputFile;
  }

  @Override
  public String toString() {
    return "HtmlReporterConfiguration{"
        + "openBrowser=" + openBrowser
        + ", htmlTemplateFile='" + htmlTemplateFile + '\''
        + ", outputFile='" + outputFile + '\''
        + '}';
  }
}
