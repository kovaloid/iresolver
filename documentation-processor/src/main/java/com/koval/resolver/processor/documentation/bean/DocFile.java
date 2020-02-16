package com.koval.resolver.processor.documentation.bean;


public class DocFile {

  private int fileIndex;
  private String fileName;

  public DocFile(int fileIndex, String fileName) {
    this.fileIndex = fileIndex;
    this.fileName = fileName;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
