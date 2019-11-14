package com.koval.jresolver.common.api.bean.result;


public class AttachmentMetric {

  private AttachmentType type;
  private int rank;
  private boolean presentInCurrentIssue;

  public AttachmentMetric(AttachmentType type, int rank, boolean presentInCurrentIssue) {
    this.type = type;
    this.rank = rank;
    this.presentInCurrentIssue = presentInCurrentIssue;
  }

  public AttachmentType getType() {
    return type;
  }

  public void setType(AttachmentType type) {
    this.type = type;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public boolean isPresentInCurrentIssue() {
    return presentInCurrentIssue;
  }

  public void setPresentInCurrentIssue(boolean presentInCurrentIssue) {
    this.presentInCurrentIssue = presentInCurrentIssue;
  }
}
