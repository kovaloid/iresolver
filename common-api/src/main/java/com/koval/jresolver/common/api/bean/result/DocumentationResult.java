package com.koval.jresolver.common.api.bean.result;

public class DocumentationResult {

  private String fileName;
  private int pageNumber;

  public DocumentationResult(String fileName, int pageNumber) {
    this.fileName = fileName;
    this.pageNumber = pageNumber;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }
}
