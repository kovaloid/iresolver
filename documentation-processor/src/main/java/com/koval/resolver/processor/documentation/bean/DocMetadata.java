package com.koval.resolver.processor.documentation.bean;


public class DocMetadata {

  private String key;
  private int fileIndex;
  private int pageNumber;

  public DocMetadata(final String key, final int fileIndex, final int pageNumber) {
    this.key = key;
    this.fileIndex = fileIndex;
    this.pageNumber = pageNumber;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(final int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(final int pageNumber) {
    this.pageNumber = pageNumber;
  }
}
