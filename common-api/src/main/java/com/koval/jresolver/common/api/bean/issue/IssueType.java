package com.koval.jresolver.common.api.bean.issue;

public class IssueType {

  private String name;
  private boolean isSubTask;

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
