package com.koval.jresolver.common.api.bean.result;


public class AttachmentResult {

  private String extension;
  private int rank;
  private String type;
  private boolean presentInCurrentIssue;

  public AttachmentResult(String extension, int rank, String type, boolean presentInCurrentIssue) {
    this.extension = extension;
    this.rank = rank;
    this.type = type;
    this.presentInCurrentIssue = presentInCurrentIssue;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isPresentInCurrentIssue() {
    return presentInCurrentIssue;
  }

  public void setPresentInCurrentIssue(boolean presentInCurrentIssue) {
    this.presentInCurrentIssue = presentInCurrentIssue;
  }
}
