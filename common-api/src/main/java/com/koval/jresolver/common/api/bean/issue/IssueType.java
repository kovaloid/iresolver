package com.koval.jresolver.common.api.bean.issue;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueType issueType = (IssueType)o;
    return isSubTask == issueType.isSubTask
        && Objects.equals(name, issueType.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, isSubTask);
  }
}
