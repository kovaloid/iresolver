package com.koval.resolver.common.api.bean.result;


public class DocumentationResult {

  private String fileName;
  private String fileUri;
  private int pageNumber;
  private double rank;

  public DocumentationResult(final String fileName, final String fileUri, final int pageNumber, final double rank) {
    this.fileName = fileName;
    this.fileUri = fileUri;
    this.pageNumber = pageNumber;
    this.rank = rank;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }

  public String getFileUri() {
    return fileUri;
  }

  public void setFileUri(final String fileUri) {
    this.fileUri = fileUri;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(final int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public double getRank() {
    return rank;
  }

  public void setRank(final double rank) {
    this.rank = rank;
  }
}
