package com.koval.resolver.processor.documentation.bean;


public class DocFile {

  private int fileIndex;
  private String fileName;

  public DocFile(final int fileIndex, final String fileName) {
    this.fileIndex = fileIndex;
    this.fileName = fileName;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(final int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }
}
