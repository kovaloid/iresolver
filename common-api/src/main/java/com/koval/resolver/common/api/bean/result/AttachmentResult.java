package com.koval.resolver.common.api.bean.result;

public class AttachmentResult {

  private String extension;
  private int rank;
  private String type;
  private boolean presentInCurrentIssue;

  public AttachmentResult(
    final String extension, final int rank, final String type, final boolean presentInCurrentIssue) {
    this.extension = extension;
    this.rank = rank;
    this.type = type;
    this.presentInCurrentIssue = presentInCurrentIssue;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(final String extension) {
    this.extension = extension;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(final int rank) {
    this.rank = rank;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public boolean isPresentInCurrentIssue() {
    return presentInCurrentIssue;
  }

  public void setPresentInCurrentIssue(final boolean presentInCurrentIssue) {
    this.presentInCurrentIssue = presentInCurrentIssue;
  }

}
