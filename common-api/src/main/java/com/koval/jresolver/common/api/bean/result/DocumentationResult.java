package com.koval.jresolver.common.api.bean.result;


public class DocumentationResult {

  private String fileName;
  private String fileUri;
  private int pageNumber;
  private double rank;

  public DocumentationResult(String fileName, String fileUri, int pageNumber, double rank) {
    this.fileName = fileName;
    this.fileUri = fileUri;
    this.pageNumber = pageNumber;
    this.rank = rank;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileUri() {
    return fileUri;
  }

  public void setFileUri(String fileUri) {
    this.fileUri = fileUri;
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
