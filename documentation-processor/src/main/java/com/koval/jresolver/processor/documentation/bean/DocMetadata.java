package com.koval.jresolver.processor.documentation.bean;


public class DocMetadata {

  private String key;
  private int fileIndex;
  private int pageNumber;

  public DocMetadata(String key, int fileIndex, int pageNumber) {
    this.key = key;
    this.fileIndex = fileIndex;
    this.pageNumber = pageNumber;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }
}
