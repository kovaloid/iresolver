package com.koval.jresolver.common.api.bean.result;

public class DocumentationResult {

  private String fileName;
  private int pageNumber;
  private double rank;

  public DocumentationResult(String fileName, int pageNumber, double rank) {
    this.fileName = fileName;
    this.pageNumber = pageNumber;
    this.rank = rank;
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

  public double getRank() {
    return rank;
  }

  public void setRank(double rank) {
    this.rank = rank;
  }
}
