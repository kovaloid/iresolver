package com.koval.jresolver.common.api.bean.issue;

public class IssueType {

  private String name;
  private boolean isSubTask;

  public IssueType() {
  }

  public IssueType(String name, boolean isSubTask) {
    this.name = name;
    this.isSubTask = isSubTask;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isSubTask() {
    return isSubTask;
  }

  public void setSubTask(boolean subTask) {
    isSubTask = subTask;
  }
}
